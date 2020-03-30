package nasm;

import c3a.*;
import ts.Ts;

public class C3a2nasm implements C3aVisitor<NasmOperand> {
    private final NasmRegister REG_EBP = new NasmRegister(Nasm.REG_EBP);
    private final NasmRegister REG_ESP = new NasmRegister(Nasm.REG_ESP);


    private final NasmRegister REG_EAX = new NasmRegister(Nasm.REG_EAX);
    private final NasmRegister REG_EBX = new NasmRegister(Nasm.REG_EBX);


    private final int INT_SIZE = 4;

    private C3a c3a;
    private Ts table;
    private Nasm nasm;

    private int currentNbLocalVar;

    public C3a2nasm(C3a c3a, Ts table) {
        this.c3a = c3a;
        this.table = table;
        this.nasm = new Nasm(table);

        REG_EBP.colorRegister(Nasm.REG_EBP);
        REG_ESP.colorRegister(Nasm.REG_ESP);
        REG_EAX.colorRegister(Nasm.REG_EAX);
        REG_EBX.colorRegister(Nasm.REG_EBX);

        nasm.ajouteInst(new NasmCall(null, new NasmLabel("main"), ""));
        nasm.ajouteInst(new NasmMov(null, REG_EBX, new NasmConstant(0), " valeur de retour du programme"));
        nasm.ajouteInst(new NasmMov(null, REG_EAX, new NasmConstant(1), ""));
        nasm.ajouteInst(new NasmInt(null, ""));

        for(C3aInst inst : c3a.listeInst) {
            inst.accept(this);
        }
    }

    public Nasm getNasm() {
        return nasm;
    }

    @Override
    public NasmOperand visit(C3aInstAdd inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstCall inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFBegin inst) {
        nasm.ajouteInst(new NasmPush(new NasmLabel(inst.val.getIdentif()), REG_EBP, ""));
        nasm.ajouteInst(new NasmMov(null, REG_EBP, REG_ESP, ""));

        currentNbLocalVar = inst.val.getNbArgs() * INT_SIZE;
        nasm.ajouteInst(new NasmSub(null, REG_ESP, new NasmConstant(currentNbLocalVar), "allocation variables local"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInst inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfLess inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstMult inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstRead inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstSub inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstAffect inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstDiv inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFEnd inst) {
        nasm.ajouteInst(new NasmAdd(null, REG_ESP, new NasmConstant(currentNbLocalVar), "desallocation variable local"));
        nasm.ajouteInst(new NasmPop(null, REG_EBP, ""));
        nasm.ajouteInst(new NasmRet(null, "Fin fonction"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfEqual inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfNotEqual inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJump inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstParam inst) {

        NasmOperand param = inst.op1.accept(this);

        nasm.ajouteInst(new NasmPush(null, param, "param"));

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstReturn inst) {

        return null;
    }

    @Override
    public NasmOperand visit(C3aInstWrite inst) {
        return null;
    }

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


    /*

    //
    //for array var: t[i]
   // t ==> array
   // i ==> index in array
    //t[i] --> adr => [t + i*4]
                    [NasmLabel + (NasmConst||NasmVar) * 4]
    

    @Override
    public NasmOperand visit(C3aVar oper) {//TODO finish this method
        if( oper.item.portee == globalTable ){
            //Global var found
            if( oper.index == null){// check if this is a simple var (oper.index == null)

            }
            else{ // or an array

            }
        }
        String varName = oper.item.identif;
        if( globalTable.getVar( varName ) != null){
            return new NasmLabel( varName );
        }
        else{
            char direction = oper.item.isParam ? '+' : '-';
            int adr =   CURRENT_NB_LOCAL_VARS - oper.item.adresse;
            NasmConstant offset = new NasmConstant( adr );
            return new NasmAddress(ebp,direction,offset);
        }
    }





     */


    @Override
    public NasmOperand visit(C3aVar oper) {
        /*if(oper.item.portee == table) {
            return new NasmLabel(oper.item.identif);
        } else {
            return new NasmAddress(REG_EBP, oper.item.isParam ? '+' : '-', new NasmConstant(oper.item.adresse));
        }*/

        if(oper.item.portee == table){
            //Global var found
            if( oper.index == null){// check if this is a simple var (oper.index == null)

            }
            else{ // or an array

            }
        }

        String varName = oper.item.identif;
        if(table.getVar(varName) != null){
            return new NasmLabel(varName);
        } else {
            char direction = oper.item.isParam ? '+' : '-';
            int adr = currentNbLocalVar - oper.item.adresse;
            NasmConstant offset = new NasmConstant(adr);
            return new NasmAddress(REG_EBP, direction, offset);
        }
    }

    @Override
    public NasmOperand visit(C3aFunction oper) {
        return new NasmLabel(oper.toString());
    }
}
