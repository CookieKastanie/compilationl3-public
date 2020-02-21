package c3a;

import sa.*;
import ts.Ts;

public class Sa2c3a extends SaDepthFirstVisitor<C3aOperand> {

    private C3a c3a;

    public Sa2c3a(SaNode root, Ts table) {
        c3a = new C3a();

        root.accept(this);
    }

    public C3a getC3a() {
        return c3a;
    }

    /*@Override
    public C3aOperand visit(SaLInst node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaDecTab node) {
        return null;
    }*/

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        C3aInstWrite ecriture = new C3aInstWrite(node.accept(this), "ecriture");
        c3a.ajouteInst(ecriture);

        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        C3aLabel labelUp = c3a.newAutoLabel();
        C3aLabel labelDown = c3a.newAutoLabel();

        c3a.addLabelToNextInst(labelUp);

        C3aOperand test = node.getTest().accept(this);
        C3aInstJumpIfEqual cond = new C3aInstJumpIfEqual(test, c3a.False, labelDown, "condition");

        c3a.ajouteInst(cond);

        node.getFaire().accept(this);

        c3a.ajouteInst(new C3aInstJump(labelUp, "jump"));

        c3a.addLabelToNextInst(labelDown);

        return null;
    }

    @Override
    public C3aOperand visit(SaInstBloc node) {
        node.accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstSi node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        C3aOperand op1 = node.getLhs().accept(this);
        C3aOperand op2 = node.getRhs().accept(this);

        c3a.ajouteInst(new C3aInstAffect(op1, op2, "affect"));

        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaLDec node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaProg node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        return new C3aVar(node.tsItem, c3a.False);
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExp node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpLire node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpInt node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAdd node) {

        SaExp exp1 = node.getOp1();
        SaExp exp2 = node.getOp2();

        C3aOperand op1 = exp1.accept(this);
        C3aOperand op2 = exp2.accept(this);

        C3aTemp temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstAdd(op1, op2, temp,"add"));

        return temp;
    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpMult node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpInf node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpEqual node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpOr node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaExpNot node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaLExp node) {
        return null;
    }

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        return null;
    }
}
