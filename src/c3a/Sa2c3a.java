package c3a;

import sa.*;
import ts.Ts;

public class Sa2c3a extends SaDepthFirstVisitor<C3aOperand> {

    private C3a c3a;

    public Sa2c3a(SaNode root) {
        c3a = new C3a();

        root.accept(this);
    }

    public C3a getC3a() {
        return c3a;
    }

    /*@Override
    public C3aOperand visit(SaLInst node) {
        return null;
    }*/

    /*@Override
    public C3aOperand visit(SaDecTab node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        C3aInstWrite ecriture = new C3aInstWrite(node.getArg().accept(this), "#ecriture");
        c3a.ajouteInst(ecriture);

        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        C3aLabel labelUp = c3a.newAutoLabel();
        C3aLabel labelDown = c3a.newAutoLabel();

        c3a.addLabelToNextInst(labelUp);

        C3aOperand test = node.getTest().accept(this);
        C3aInstJumpIfEqual cond = new C3aInstJumpIfEqual(test, c3a.False, labelDown, "#condition");

        c3a.ajouteInst(cond);

        node.getFaire().accept(this);

        c3a.ajouteInst(new C3aInstJump(labelUp, "#jump"));

        c3a.addLabelToNextInst(labelDown);

        return null;
    }

    /*@Override
    public C3aOperand visit(SaInstBloc node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaInstSi node) {
        C3aLabel labelEndIf = c3a.newAutoLabel();

        C3aOperand test = node.getTest().accept(this);

        SaInst sinon = node.getSinon();
        if(sinon != null) {
            C3aLabel labelElse = c3a.newAutoLabel();

            C3aInstJumpIfEqual cond = new C3aInstJumpIfEqual(test, c3a.False, labelElse, "#condition");
            c3a.ajouteInst(cond);
            node.getAlors().accept(this);

            c3a.ajouteInst(new C3aInstJump(labelEndIf, "#jump to endif"));

            c3a.addLabelToNextInst(labelElse);
            sinon.accept(this);
        } else {
            C3aInstJumpIfEqual cond = new C3aInstJumpIfEqual(test, c3a.False, labelEndIf, "#condition");
            c3a.ajouteInst(cond);
            node.getAlors().accept(this);
        }

        c3a.addLabelToNextInst(labelEndIf);

        return null;
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        C3aOperand op1 = node.getRhs().accept(this);
        C3aOperand op2 = node.getLhs().accept(this);

        c3a.ajouteInst(new C3aInstAffect(op1, op2, "#affect"));

        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        C3aOperand val = node.getVal().accept(this);
        c3a.ajouteInst(new C3aInstReturn(val, "#return"));

        return null;
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        c3a.ajouteInst(new C3aInstFBegin(node.tsItem, node.getNom()));
        node.getCorps().accept(this);
        c3a.ajouteInst(new C3aInstFEnd(node.getNom()));
        return null;
    }

    /*@Override
    public C3aOperand visit(SaDecVar node) {
        return null;
    }*/

    /*@Override
    public C3aOperand visit(SaLDec node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaProg node) {
        //node.getVariables().accept(this);
        node.getFonctions().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        return new C3aVar(node.tsItem, null);
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        if(node.getArguments() != null) {
            SaLExp arguments = node.getArguments();
            while (arguments != null) {
                C3aOperand param = arguments.getTete().accept(this);
                c3a.ajouteInst(new C3aInstParam(param, ""));
                arguments = arguments.getQueue();
            }
        }

        C3aOperand temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstCall(new C3aFunction(node.tsItem), temp, ""));

        return temp;
    }

    /*@Override
    public C3aOperand visit(SaExp node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaExpLire node) {
        C3aOperand temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstRead(temp, "#read"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpInt node) {
        return new C3aConstant(node.getVal());
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        return node.getVar().accept(this);
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        C3aOperand temp = node.getVal().accept(this);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAdd node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstAdd(op1, op2, temp,"#add"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpSub node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstSub(op1, op2, temp,"#sub"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpMult node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstMult(op1, op2, temp,"#mult"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstDiv(op1, op2, temp,"#div"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpInf node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aLabel label = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp, ""));

        c3a.ajouteInst(new C3aInstJumpIfLess(op1, op2, label,"#inf"));

        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));

        c3a.addLabelToNextInst(label);

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpEqual node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aLabel label = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp , ""));

        c3a.ajouteInst(new C3aInstJumpIfEqual(op1, op2, label,"#equal"));

        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));

        c3a.addLabelToNextInst(label);

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aLabel valid = c3a.newAutoLabel();
        C3aLabel invalid = c3a.newAutoLabel();

        c3a.ajouteInst(new C3aInstJumpIfEqual(op1, c3a.False, invalid,"#and 1"));
        c3a.ajouteInst(new C3aInstJumpIfEqual(op2, c3a.False, invalid,"#and 2"));

        C3aTemp temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp , ""));

        c3a.ajouteInst(new C3aInstJump(valid, ""));

        c3a.addLabelToNextInst(invalid);
        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));

        c3a.addLabelToNextInst(valid);

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpOr node) {

        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        C3aLabel valid = c3a.newAutoLabel();
        C3aLabel invalid = c3a.newAutoLabel();

        c3a.ajouteInst(new C3aInstJumpIfNotEqual(op1, c3a.False, valid,"#or 1"));
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(op2, c3a.False, valid,"#or 2"));

        C3aTemp temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));

        c3a.ajouteInst(new C3aInstJump(invalid, ""));

        c3a.addLabelToNextInst(valid);
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp, ""));

        c3a.addLabelToNextInst(invalid);

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpNot node) {

        C3aOperand op = node.getOp1().accept(this);

        C3aLabel labelA = c3a.newAutoLabel();
        C3aLabel labelB = c3a.newAutoLabel();

        c3a.ajouteInst(new C3aInstJumpIfEqual(op, c3a.False, labelA,"#not"));

        C3aTemp temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstAffect(c3a.True, temp, ""));
        c3a.ajouteInst(new C3aInstJump(labelB, ""));

        c3a.addLabelToNextInst(labelA);
        c3a.ajouteInst(new C3aInstAffect(c3a.False, temp, ""));

        c3a.addLabelToNextInst(labelB);

        return temp;
    }

    /*@Override
    public C3aOperand visit(SaLExp node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        return new C3aVar(node.tsItem, node.getIndice().accept(this));
    }
}
