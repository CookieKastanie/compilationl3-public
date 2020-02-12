package sa;

import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {
    private SaNode returnValue;

    public SaNode getRoot() {
        return returnValue;
    }


    // A
    @Override
    public void caseAWithVarProgram(AWithVarProgram node) {
        SaLDec variables = null;
        SaLDec fonctions = null;

        if(node.getDecLVar() != null) {
            node.getDecLVar().apply(this);
            variables = (SaLDec) returnValue;
        }

        if(node.getPv() != null) {
            node.getPv().apply(this);
        }

        if(node.getDecLFunc() != null) {
            node.getDecLFunc().apply(this);
            fonctions = (SaLDec) returnValue;
        }

        returnValue = new SaProg(variables, fonctions);
    }

    @Override
    public void caseANoVarProgram(ANoVarProgram node) {
        SaLDec fonctions = null;

        if(node.getDecLFunc() != null) {
            node.getDecLFunc().apply(this);
            fonctions = (SaLDec) returnValue;
        }

        returnValue = new SaProg(null, fonctions);
    }



    // B
    @Override
    public void caseAVarDecLVar(AVarDecLVar node) {
        SaDec decVar = null;
        SaLDec lDec = null;

        if(node.getDecVar() != null) {
            node.getDecVar().apply(this);
            decVar = (SaDec) returnValue;
        }
        if(node.getDecLVarB() != null) {
            node.getDecLVarB().apply(this);
            lDec = (SaLDec) returnValue;
        }

        returnValue = new SaLDec(decVar, lDec);
    }

    @Override
    public void caseANoVarDecLVar(ANoVarDecLVar node) {
        returnValue = null;
    }

    @Override
    public void caseAVarDecLVarB(AVarDecLVarB node) {
        SaDec decVar = null;
        SaLDec lDec = null;

        if(node.getDecVar() != null) {
            node.getDecVar().apply(this);
            decVar = (SaDec) returnValue;
        }
        if(node.getDecLVarB() != null) {
            node.getDecLVarB().apply(this);
            lDec = (SaLDec) returnValue;
        }

        returnValue = new SaLDec(decVar, lDec);
    }

    @Override
    public void caseAEndDecLVarB(AEndDecLVarB node) {
        returnValue = null;
    }

    @Override
    public void caseAEntierDecVar(AEntierDecVar node) {
        String nom = null;

        if(node.getEntier() != null) {
            node.getEntier().apply(this);
        }
        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }

        returnValue = new SaDecVar(nom);
    }

    @Override
    public void caseATabDecVar(ATabDecVar node) {
        String nom = null;
        int taille = 0;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }

        if(node.getConst() != null)
        {
            node.getConst().apply(this);
            taille = Integer.parseInt(node.getConst().getText());
        }

        returnValue = new SaDecTab(nom, taille);
    }



    // C

    @Override
    public void caseAFuncDecLFunc(AFuncDecLFunc node) {
        SaDec fonc = null;
        SaLDec lFonc = null;

        if(node.getDecFunc() != null) {
            node.getDecFunc().apply(this);
            fonc = (SaDec) returnValue;
        }
        if(node.getDecLFunc() != null) {
            node.getDecLFunc().apply(this);
            lFonc = (SaLDec) returnValue;
        }

        returnValue = new SaLDec(fonc, lFonc);
    }

    @Override
    public void caseANoFuncDecLFunc(ANoFuncDecLFunc node) {
        returnValue = null;
    }

    @Override
    public void caseAWithVarDecFunc(AWithVarDecFunc node) {
        String nom = null;
        SaLDec params = null;
        SaLDec vars = null;
        SaInst insts = null;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }
        if(node.getArgs() != null) {
            node.getArgs().apply(this);
            params = (SaLDec) returnValue;
        }
        if(node.getVars() != null) {
            node.getVars().apply(this);
            vars = (SaLDec) returnValue;
        }
        if(node.getBloc() != null) {
            node.getBloc().apply(this);
            insts = (SaInst) returnValue;
        }

        returnValue = new SaDecFonc(nom, params, vars, insts);
    }

    @Override
    public void caseANoVarDecFunc(ANoVarDecFunc node) {
        String nom = null;
        SaLDec params = null;
        SaInst insts = null;
        //SaInstBloc insts = null;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }
        if(node.getDecLVar() != null) {
            node.getDecLVar().apply(this);
            params = (SaLDec) returnValue;
        }
        if(node.getBloc() != null) {
            node.getBloc().apply(this);
            insts = (SaInst) returnValue;
        }

        returnValue = new SaDecFonc(nom, params, null, insts);
    }



    // D

    @Override
    public void caseABloc(ABloc node) {
        SaLInst lInst = null;

        if(node.getLInst() != null) {
            node.getLInst().apply(this);
            lInst = (SaLInst) returnValue;
        }

        returnValue = new SaInstBloc(lInst);
    }

    @Override
    public void caseAInstLInst(AInstLInst node) {
        SaInst inst = null;
        SaLInst lInst = null;

        if(node.getInst() != null) {
            node.getInst().apply(this);
            inst = (SaInst) returnValue;
        }
        if(node.getLInst() != null) {
            node.getLInst().apply(this);
            lInst = (SaLInst) returnValue;
        }

        returnValue = new SaLInst(inst, lInst);
    }

    @Override
    public void caseANoInstLInst(ANoInstLInst node) {
        returnValue = null;
    }

    @Override
    public void caseABlocInst(ABlocInst node) {
        SaInstBloc bloc = null;

        if(node.getBloc() != null) {
            node.getBloc().apply(this);
            bloc = (SaInstBloc) returnValue;
        }

        returnValue = bloc;
    }

    @Override
    public void caseASiInst(ASiInst node) {
        SaInst i = null;

        if(node.getISi() != null) {
            node.getISi().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }

    @Override
    public void caseATantqueInst(ATantqueInst node) {
        SaInst i = null;

        if(node.getITantque() != null) {
            node.getITantque().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }

    @Override
    public void caseAAffInst(AAffInst node) {
        SaInst i = null;

        if(node.getIAff() != null) {
            node.getIAff().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }

    @Override
    public void caseARetourInst(ARetourInst node) {
        SaInst i = null;

        if(node.getIRetour() != null) {
            node.getIRetour().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }

    @Override
    public void caseAFuncInst(AFuncInst node) {
        SaInst i = null;

        if(node.getIFunc() != null) {
            node.getIFunc().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }

    @Override
    public void caseAEcrireInst(AEcrireInst node) {
        SaInst i = null;

        if(node.getIEcrire() != null) {
            node.getIEcrire().apply(this);
            i = (SaInst) returnValue;
        }
        returnValue = i;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void caseAIAff(AIAff node) {
        SaVar var = null;
        SaExp exp = null;

        if(node.getVar() != null) {
            node.getVar().apply(this);
            var = (SaVar) returnValue;
        }
        if(node.getEgale() != null) {
            node.getEgale().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getPv() != null) {
            node.getPv().apply(this);
        }

        returnValue = new SaInstAffect(var, exp);
    }

    @Override
    public void caseAWithSinonISi(AWithSinonISi node) {
        SaExp exp = null;
        SaInst bloc1 = null;
        SaInst bloc2 = null;

        if(node.getSi() != null) {
            node.getSi().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getAlors() != null) {
            node.getAlors().apply(this);
        }
        if(node.getSiBloc() != null) {
            node.getSiBloc().apply(this);
            bloc1 = (SaInst) returnValue;
        }
        if(node.getSinon() != null) {
            node.getSinon().apply(this);
        }
        if(node.getSinonBloc() != null) {
            node.getSinonBloc().apply(this);
            bloc2 = (SaInst) returnValue;
        }

        returnValue = new SaInstSi(exp, bloc1, bloc2);
    }

    @Override
    public void caseANoSinonISi(ANoSinonISi node) {
        SaExp exp = null;
        SaInst bloc = null;

        if(node.getSi() != null) {
            node.getSi().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getAlors() != null) {
            node.getAlors().apply(this);
        }
        if(node.getBloc() != null) {
            node.getBloc().apply(this);
            bloc = (SaInst) returnValue;
        }

        returnValue = new SaInstSi(exp, bloc, null);
    }

    @Override
    public void caseAITantque(AITantque node) {
        SaExp exp = null;
        SaInst bloc = null;

        if(node.getTantque() != null) {
            node.getTantque().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getFaire() != null) {
            node.getFaire().apply(this);
        }
        if(node.getBloc() != null) {
            node.getBloc().apply(this);
            bloc = (SaInst) returnValue;
        }
        returnValue = new SaInstTantQue(exp, bloc);
    }

    @Override
    public void caseAIRetour(AIRetour node) {
        SaExp exp = null;

        if(node.getRetour() != null) {
            node.getRetour().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getPv() != null) {
            node.getPv().apply(this);
        }

        returnValue = new SaInstRetour(exp);
    }

    @Override
    public void caseAAppFuncIFunc(AAppFuncIFunc node) {
        SaAppel appel = null;

        if(node.getFunc() != null) {
            node.getFunc().apply(this);
            appel = (SaAppel) returnValue;
        }
        if(node.getPv() != null) {
            node.getPv().apply(this);
        }

        returnValue = appel;
    }

    @Override
    public void caseAAppLireIFunc(AAppLireIFunc node) {
        if(node.getLire() != null) {
            node.getLire().apply(this);
        }
        if(node.getPo() != null) {
            node.getPo().apply(this);
        }
        if(node.getPf() != null) {
            node.getPf().apply(this);
        }
        if(node.getPv() != null) {
            node.getPv().apply(this);
        }
        returnValue = new SaExpLire();
    }

    @Override
    public void caseAIEcrire(AIEcrire node) {
        SaExp exp = null;

        if(node.getExp() != null){
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }

        returnValue = new SaInstEcriture(exp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void caseAEntierVar(AEntierVar node) {
        String nom = null;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }

        returnValue = new SaVarSimple(nom);
    }

    @Override
    public void caseATabEntierVar(ATabEntierVar node) {
        String nom = null;
        SaExp indice = null;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }
        if(node.getCo() != null) {
            node.getCo().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            indice = (SaExp) returnValue;
        }
        if(node.getCf() != null) {
            node.getCf().apply(this);
        }

        returnValue = new SaVarIndicee(nom, indice);
    }

    @Override
    public void caseAFunc(AFunc node) {
        String nom = null;
        SaLExp args = null;

        if(node.getIdent() != null) {
            node.getIdent().apply(this);
            nom = node.getIdent().getText();
        }
        if(node.getPo() != null) {
            node.getPo().apply(this);
        }
        if(node.getFuncLArg() != null) {
            node.getFuncLArg().apply(this);
            args = (SaLExp) returnValue;
        }
        if(node.getPf() != null) {
            node.getPf().apply(this);
        }

        returnValue = new SaAppel(nom, args);
    }

    @Override
    public void caseAArgFuncLArg(AArgFuncLArg node) {
        SaExp exp = null;
        SaLExp lExp = null;

        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getArg() != null) {
            node.getArg().apply(this);
            lExp = (SaLExp) returnValue;
        }
        returnValue = new SaLExp(exp, lExp);
    }

    @Override
    public void caseANoArgFuncLArg(ANoArgFuncLArg node) {
        returnValue = null;
    }

    @Override
    public void caseAExpArg(AExpArg node) {
        SaExp exp = null;
        SaLExp lExp = null;

        if(node.getVirg() != null) {
            node.getVirg().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            exp = (SaExp) returnValue;
        }
        if(node.getArg() != null) {
            node.getArg().apply(this);
            lExp = (SaLExp) returnValue;
        }

        returnValue = new SaLExp(exp, lExp);
    }

    @Override
    public void caseANoExpArg(ANoExpArg node) {
        returnValue = null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void caseAOuExp(AOuExp node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp() != null) {
            node.getExp().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getOu() != null) {
            node.getOu().apply(this);
        }
        if(node.getExp1() != null) {
            node.getExp1().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpOr(op1, op2);
    }

    @Override
    public void caseAToExp1Exp(AToExp1Exp node) {
        SaExp e = null;

        if(node.getExp1() != null) {
            node.getExp1().apply(this);
            e = (SaExp) returnValue;
        }

        returnValue = e;
    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp1() != null) {
            node.getExp1().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getEt() != null) {
            node.getEt().apply(this);
        }
        if(node.getExp2() != null) {
            node.getExp2().apply(this);
            op2 = (SaExp) returnValue;
        }

        returnValue = new SaExpAnd(op1, op2);
    }

    @Override
    public void caseAToExp2Exp1(AToExp2Exp1 node) {
        SaExp e = null;

        if(node.getExp2() != null) {
            node.getExp2().apply(this);
            e = (SaExp) returnValue;
        }
        returnValue = e;
    }

    @Override
    public void caseAEgaleExp2(AEgaleExp2 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp2() != null) {
            node.getExp2().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getEgale() != null) {
            node.getEgale().apply(this);
        }
        if(node.getExp3() != null) {
            node.getExp3().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpEqual(op1, op2);
    }

    @Override
    public void caseAInfExp2(AInfExp2 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp2() != null) {
            node.getExp2().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getInf() != null) {
            node.getInf().apply(this);
        }
        if(node.getExp3() != null) {
            node.getExp3().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpInf(op1, op2);
    }

    @Override
    public void caseAToExp3Exp2(AToExp3Exp2 node) {
        SaExp e = null;

        if(node.getExp3() != null) {
            node.getExp3().apply(this);
            e = (SaExp) returnValue;
        }
        returnValue = e;
    }

    @Override
    public void caseAPlusExp3(APlusExp3 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp3() != null) {
            node.getExp3().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getPlus() != null) {
            node.getPlus().apply(this);
        }
        if(node.getExp4() != null) {
            node.getExp4().apply(this);
            op2 = (SaExp) returnValue;
        }

        returnValue = new SaExpAdd(op1, op2);
    }

    @Override
    public void caseAMoinsExp3(AMoinsExp3 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp3() != null) {
            node.getExp3().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getMoins() != null) {
            node.getMoins().apply(this);
        }
        if(node.getExp4() != null) {
            node.getExp4().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpSub(op1, op2);
    }

    @Override
    public void caseAToExp4Exp3(AToExp4Exp3 node) {
        SaExp e = null;

        if(node.getExp4() != null) {
            node.getExp4().apply(this);
            e = (SaExp) returnValue;
        }
        returnValue = e;
    }

    @Override
    public void caseAMultExp4(AMultExp4 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp4() != null) {
            node.getExp4().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getMult() != null) {
            node.getMult().apply(this);
        }
        if(node.getExp5() != null) {
            node.getExp5().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpMult(op1, op2);
    }

    @Override
    public void caseADivExp4(ADivExp4 node) {
        SaExp op1 = null;
        SaExp op2 = null;

        if(node.getExp4() != null) {
            node.getExp4().apply(this);
            op1 = (SaExp) returnValue;
        }
        if(node.getDiv() != null) {
            node.getDiv().apply(this);
        }
        if(node.getExp5() != null) {
            node.getExp5().apply(this);
            op2 = (SaExp) returnValue;
        }
        returnValue = new SaExpDiv(op1, op2);
    }

    @Override
    public void caseAToExp5Exp4(AToExp5Exp4 node) {
        SaExp e = null;

        if(node.getExp5() != null) {
            node.getExp5().apply(this);
            e = (SaExp) returnValue;
        }
        returnValue = e;
    }

    @Override
    public void caseANonExp5(ANonExp5 node) {
        SaExp op1 = null;

        if(node.getNon() != null) {
            node.getNon().apply(this);
        }
        if(node.getExp5() != null) {
            node.getExp5().apply(this);
            op1 = (SaExp) returnValue;
        }
        returnValue = new SaExpNot(op1);
    }

    @Override
    public void caseAToExp6Exp5(AToExp6Exp5 node) {
        SaExp e = null;

        if(node.getExp6() != null) {
            node.getExp6().apply(this);
            e = (SaExp) returnValue;
        }
        returnValue = e;
    }

    @Override
    public void caseAExpParExp6(AExpParExp6 node) {
        SaExp e = null;

        if(node.getPo() != null) {
            node.getPo().apply(this);
        }
        if(node.getExp() != null) {
            node.getExp().apply(this);
            e = (SaExp) returnValue;
        }
        if(node.getPf() != null) {
            node.getPf().apply(this);
        }
        returnValue = e;
    }

    @Override
    public void caseAConstExp6(AConstExp6 node) {
        int val = 0;

        if(node.getConst() != null) {
            node.getConst().apply(this);
            val = Integer.parseInt(node.getConst().getText());
        }

        returnValue = new SaExpInt(val);
    }

    @Override
    public void caseAFuncExp6(AFuncExp6 node) {
        SaAppel func = null;

        if(node.getFunc() != null) {
            node.getFunc().apply(this);
            func = (SaAppel) returnValue;
        }

        returnValue = new SaExpAppel(func);
    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {
        SaVar var = null;

        if(node.getVar() != null) {
            node.getVar().apply(this);
            var = (SaVar) returnValue;
        }

        returnValue = new SaExpVar(var);
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        if(node.getLire() != null) {
            node.getLire().apply(this);
        }
        if(node.getPo() != null) {
            node.getPo().apply(this);
        }
        if(node.getPf() != null) {
            node.getPf().apply(this);
        }

        returnValue = new SaExpLire();
    }

    @Override
    public void caseEOF(EOF node) {
        // Ne rien faire
    }

    @Override
    public void caseInvalidToken(InvalidToken node) {
        System.out.println("!!! Invalid Token !!!");
        returnValue = null;
    }
}
