package sa;

import sc.analysis.Analysis;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

import java.sql.SQLOutput;

public class Sc2sa extends DepthFirstAdapter/* implements Analysis*/{
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

        if(node.getVirg() != null) {
            node.getVirg().apply(this);
        }
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
        SaLInst // BRUH

        if(node.getLInst() != null) {
            node.getLInst().apply(this);
        }
    }

    @Override
    public void caseAInstLInst(AInstLInst node) {

    }

    @Override
    public void caseANoInstLInst(ANoInstLInst node) {

    }

    @Override
    public void caseASiInst(ASiInst node) {

    }

    @Override
    public void caseATantqueInst(ATantqueInst node) {

    }

    @Override
    public void caseAAffInst(AAffInst node) {

    }

    @Override
    public void caseARetourInst(ARetourInst node) {

    }

    @Override
    public void caseAFuncInst(AFuncInst node) {

    }

    @Override
    public void caseAEcrireInst(AEcrireInst node) {

    }

    @Override
    public void caseAIAff(AIAff node) {

    }

    @Override
    public void caseAWithSinonISi(AWithSinonISi node) {

    }

    @Override
    public void caseANoSinonISi(ANoSinonISi node) {

    }

    @Override
    public void caseAITantque(AITantque node) {

    }

    @Override
    public void caseAIRetour(AIRetour node) {

    }

    @Override
    public void caseAAppFuncIFunc(AAppFuncIFunc node) {

    }

    @Override
    public void caseAAppLireIFunc(AAppLireIFunc node) {

    }

    @Override
    public void caseAIEcrire(AIEcrire node) {

    }

    @Override
    public void caseAEntierVar(AEntierVar node) {

    }

    @Override
    public void caseATabEntierVar(ATabEntierVar node) {

    }

    @Override
    public void caseAFunc(AFunc node) {

    }

    @Override
    public void caseAArgFuncLArg(AArgFuncLArg node) {

    }

    @Override
    public void caseANoArgFuncLArg(ANoArgFuncLArg node) {

    }

    @Override
    public void caseAExpArg(AExpArg node) {

    }

    @Override
    public void caseANoExpArg(ANoExpArg node) {

    }

    @Override
    public void caseAOuExp(AOuExp node) {

    }

    @Override
    public void caseAToExp1Exp(AToExp1Exp node) {

    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {

    }

    @Override
    public void caseAToExp2Exp1(AToExp2Exp1 node) {

    }

    @Override
    public void caseAEgaleExp2(AEgaleExp2 node) {

    }

    @Override
    public void caseAInfExp2(AInfExp2 node) {

    }

    @Override
    public void caseAToExp3Exp2(AToExp3Exp2 node) {

    }

    @Override
    public void caseAPlusExp3(APlusExp3 node) {

    }

    @Override
    public void caseAMoinsExp3(AMoinsExp3 node) {

    }

    @Override
    public void caseAToExp4Exp3(AToExp4Exp3 node) {

    }

    @Override
    public void caseAMultExp4(AMultExp4 node) {

    }

    @Override
    public void caseADivExp4(ADivExp4 node) {

    }

    @Override
    public void caseAToExp5Exp4(AToExp5Exp4 node) {

    }

    @Override
    public void caseANonExp5(ANonExp5 node) {

    }

    @Override
    public void caseAToExp6Exp5(AToExp6Exp5 node) {

    }

    @Override
    public void caseAExpParExp6(AExpParExp6 node) {

    }

    @Override
    public void caseAConstExp6(AConstExp6 node) {

    }

    @Override
    public void caseAFuncExp6(AFuncExp6 node) {

    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {

    }

    @Override
    public void caseALireExp6(ALireExp6 node) {

    }

    @Override
    public void caseTEntier(TEntier node) {

    }

    @Override
    public void caseTLire(TLire node) {

    }

    @Override
    public void caseTEcrire(TEcrire node) {

    }

    @Override
    public void caseTOu(TOu node) {

    }

    @Override
    public void caseTEt(TEt node) {

    }

    @Override
    public void caseTNon(TNon node) {

    }

    @Override
    public void caseTEgale(TEgale node) {

    }

    @Override
    public void caseTInf(TInf node) {

    }

    @Override
    public void caseTPlus(TPlus node) {

    }

    @Override
    public void caseTMoins(TMoins node) {

    }

    @Override
    public void caseTMult(TMult node) {

    }

    @Override
    public void caseTDiv(TDiv node) {

    }

    @Override
    public void caseTPo(TPo node) {

    }

    @Override
    public void caseTPf(TPf node) {

    }

    @Override
    public void caseTAo(TAo node) {

    }

    @Override
    public void caseTAf(TAf node) {

    }

    @Override
    public void caseTCo(TCo node) {

    }

    @Override
    public void caseTCf(TCf node) {

    }

    @Override
    public void caseTVirg(TVirg node) {

    }

    @Override
    public void caseTPv(TPv node) {

    }

    @Override
    public void caseTSi(TSi node) {

    }

    @Override
    public void caseTSinon(TSinon node) {

    }

    @Override
    public void caseTAlors(TAlors node) {

    }

    @Override
    public void caseTTantque(TTantque node) {

    }

    @Override
    public void caseTFaire(TFaire node) {

    }

    @Override
    public void caseTRetour(TRetour node) {

    }

    @Override
    public void caseTEspaces(TEspaces node) {

    }

    @Override
    public void caseTCommentaire(TCommentaire node) {

    }

    @Override
    public void caseTConst(TConst node) {

    }

    @Override
    public void caseTIdent(TIdent node) {

    }

    @Override
    public void caseEOF(EOF node) {

    }

    @Override
    public void caseInvalidToken(InvalidToken node) {

    }
}