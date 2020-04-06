package nasm;

import c3a.*;
import ts.Ts;

public class C3a2nasm implements C3aVisitor<NasmOperand> {
    private final NasmRegister REG_EBP = new NasmRegister(Nasm.REG_EBP);
    private final NasmRegister REG_ESP = new NasmRegister(Nasm.REG_ESP);

    private final int INT_SIZE = 4;

    private C3a c3a;
    private Ts table;
    private Nasm nasm;

    private int currentLocalVarOffset;

    public C3a2nasm(C3a c3a, Ts table) {
        this.c3a = c3a;
        this.table = table;
        this.nasm = new Nasm(table);

        REG_EBP.colorRegister(Nasm.REG_EBP);
        REG_ESP.colorRegister(Nasm.REG_ESP);

        nasm.setTempCounter(c3a.getTempCounter());

        NasmRegister ebx = nasm.newRegister();
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);
        ebx.colorRegister(Nasm.REG_EBX);

        nasm.ajouteInst(new NasmCall(null, new NasmLabel("main"), ""));
        nasm.ajouteInst(new NasmMov(null, ebx, new NasmConstant(0), " valeur de retour du programme"));
        nasm.ajouteInst(new NasmMov(null, eax, new NasmConstant(1), ""));

        nasm.ajouteInst(new NasmInt(null, ""));

        for(C3aInst inst : c3a.listeInst) inst.accept(this);
    }

    private NasmOperand label(C3aInst inst) {
        if(inst.label != null) return inst.label.accept(this);
        return null;
    }

    public Nasm getNasm() {
        return nasm;
    }

    @Override
    public NasmOperand visit(C3aInstAdd inst) {
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);
        nasm.ajouteInst(new NasmMov(label(inst), dest, op1, ""));
        nasm.ajouteInst(new NasmAdd(null, dest, op2, ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstCall inst) {
        nasm.ajouteInst(new NasmSub(label(inst), REG_ESP, new NasmConstant(INT_SIZE), "allocation valeur retour"));
        nasm.ajouteInst(new NasmCall(null, inst.op1.accept(this), ""));
        nasm.ajouteInst(new NasmPop(null, inst.result.accept(this), "valeur retour"));

        if (inst.op1.val.getNbArgs() > 0) {
            nasm.ajouteInst(new NasmAdd(null, REG_ESP, new NasmConstant(inst.op1.val.getNbArgs() * INT_SIZE), "désallocation des arguments"));
        }

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFBegin inst) {
        nasm.ajouteInst(new NasmPush(new NasmLabel(inst.val.getIdentif()), REG_EBP, ""));
        nasm.ajouteInst(new NasmMov(null, REG_EBP, REG_ESP, ""));

        currentLocalVarOffset = inst.val.getTable().nbVar() * INT_SIZE;
        nasm.ajouteInst(new NasmSub(null, REG_ESP, new NasmConstant(currentLocalVarOffset), "allocation variables local"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfLess inst) {
        nasm.ajouteInst(new NasmCmp(null, inst.op1.accept(this), inst.op2.accept(this), ""));
        nasm.ajouteInst(new NasmJl(label(inst), inst.result.accept(this), ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstMult inst) {
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);
        nasm.ajouteInst(new NasmMov(label(inst), dest, op1, ""));
        nasm.ajouteInst(new NasmMul(null, dest, op2, ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstRead inst) {
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label(inst), eax, new NasmConstant(2), ""));
        nasm.ajouteInst(new NasmCall(null, new NasmLabel("readline"), ""));
        nasm.ajouteInst(new NasmCall(null, new NasmLabel("atoi"), ""));
        nasm.ajouteInst(new NasmMov(null, inst.result.accept(this), eax, ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstSub inst) {
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);
        nasm.ajouteInst(new NasmMov(label(inst), dest, op1, ""));
        nasm.ajouteInst(new NasmSub(null, dest, op2, ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstAffect inst) {
        nasm.ajouteInst(new NasmMov(label(inst), inst.result.accept(this), inst.op1.accept(this), ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstDiv inst) {
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label(inst), eax, op1, ""));

        if (op2 instanceof NasmConstant) {
            NasmOperand reg = nasm.newRegister();
            nasm.ajouteInst(new NasmMov(null, reg, op2, ""));
            nasm.ajouteInst(new NasmDiv(null, reg, ""));
        }
        else {
            nasm.ajouteInst(new NasmDiv(null, op2, ""));
        }

        nasm.ajouteInst(new NasmMov(null, dest, eax, ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFEnd inst) {
        nasm.ajouteInst(new NasmAdd(label(inst), REG_ESP, new NasmConstant(currentLocalVarOffset), "desallocation variable local"));
        nasm.ajouteInst(new NasmPop(null, REG_EBP, ""));
        nasm.ajouteInst(new NasmRet(null, "Fin fonction"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfEqual inst) {
        nasm.ajouteInst(new NasmCmp(label(inst), inst.op1.accept(this), inst.op2.accept(this), ""));
        nasm.ajouteInst(new NasmJe(null, inst.result.accept(this), ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfNotEqual inst) {
        nasm.ajouteInst(new NasmCmp(label(inst), inst.op1.accept(this), inst.op2.accept(this), ""));
        nasm.ajouteInst(new NasmJne(null, inst.result.accept(this), ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJump inst) {
        nasm.ajouteInst(new NasmJmp(label(inst), inst.result.accept(this), ""));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstParam inst) {
        NasmOperand param = inst.op1.accept(this);
        nasm.ajouteInst(new NasmPush(label(inst), param, "param"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstReturn inst) {
        NasmAddress address = new NasmAddress(REG_EBP, '+', new NasmConstant(2));
        nasm.ajouteInst(new NasmMov(label(inst), address, inst.op1.accept(this), "ecriture de la valeur de retour"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstWrite inst) {
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label(inst), eax, inst.op1.accept(this), "Write 1"));
        nasm.ajouteInst(new NasmCall(null, new NasmLabel("iprintLF"), "Write 2"));

        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public NasmOperand visit(C3aConstant oper) {
        return new NasmConstant(oper.val);
    }

    @Override
    public NasmOperand visit(C3aLabel oper) {
        return new NasmLabel(oper.toString());
    }

    @Override
    public NasmOperand visit(C3aTemp oper) {
        return new NasmRegister(oper.num);
    }

    @Override
    public NasmOperand visit(C3aVar oper) {
        if (oper.item.portee == table) {
            NasmLabel identif = new NasmLabel(oper.item.getIdentif());

            if (oper.item.getTaille() <= 1) {
                return new NasmAddress(identif);
            } else {
                if(oper.index instanceof C3aConstant) {
                    NasmConstant offset = new NasmConstant(((C3aConstant) oper.index).val);
                    return new NasmAddress(identif, '+', offset);
                } else if(oper.index instanceof C3aTemp){
                    NasmRegister offset = new NasmRegister(((C3aTemp) oper.index).num);
                    return new NasmAddress(identif, '+', offset);
                } else {
                    NasmAddress offset = (NasmAddress) oper.index.accept(this);
                    return new NasmAddress(identif, '+', offset);
                }
            }
        } else {
            if (oper.item.isParam) {
                NasmConstant offset = new NasmConstant(2 + (oper.item.portee.nbArg() - oper.item.getAdresse()));
                return new NasmAddress(REG_EBP, '+', offset);
            } else {
                NasmConstant offset = new NasmConstant(1 + oper.item.getAdresse());
                return new NasmAddress(REG_EBP, '-', offset);
            }
        }
    }

    @Override
    public NasmOperand visit(C3aFunction oper) {
        return new NasmLabel(oper.toString());
    }

    @Override
    public NasmOperand visit(C3aInst inst) {
        return null;
    }
}
