package sa;

import sc.analysis.Analysis;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

import java.sql.SQLOutput;

public class Sc2sa extends DepthFirstAdapter /*implements Analysis*/{
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
        returnValue = null;
    }

    @Override
    public void caseATantqueInst(ATantqueInst node) {
        returnValue = null;
    }

    @Override
    public void caseAAffInst(AAffInst node) {
        returnValue = null;
    }

    @Override
    public void caseARetourInst(ARetourInst node) {
        returnValue = null;
    }

    @Override
    public void caseAFuncInst(AFuncInst node) {
        returnValue = null;
    }

    @Override
    public void caseAEcrireInst(AEcrireInst node) {
        SaExp e = null;

        if(node.getIEcrire() != null) {
            node.getIEcrire().apply(this); ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            e = (SaExp) returnValue;
        }
        returnValue = new SaInstEcriture(e);
    }

    @Override
    public void caseAIAff(AIAff node) {
        returnValue = null;
    }

    @Override
    public void caseAWithSinonISi(AWithSinonISi node) {
        returnValue = null;
    }

    @Override
    public void caseANoSinonISi(ANoSinonISi node) {
        returnValue = null;
    }

    @Override
    public void caseAITantque(AITantque node) {
        returnValue = null;
    }

    @Override
    public void caseAIRetour(AIRetour node) {
        returnValue = null;
    }

    @Override
    public void caseAAppFuncIFunc(AAppFuncIFunc node) {
        returnValue = null;
    }

    @Override
    public void caseAAppLireIFunc(AAppLireIFunc node) {
        returnValue = null;
    }

    @Override
    public void caseAIEcrire(AIEcrire node) {
        returnValue = null;
    }

    @Override
    public void caseAEntierVar(AEntierVar node) {
        returnValue = null;
    }

    @Override
    public void caseATabEntierVar(ATabEntierVar node) {
        returnValue = null;
    }

    @Override
    public void caseAFunc(AFunc node) {
        returnValue = null;
    }

    @Override
    public void caseAArgFuncLArg(AArgFuncLArg node) {
        returnValue = null;
    }

    @Override
    public void caseANoArgFuncLArg(ANoArgFuncLArg node) {
        returnValue = null;
    }

    @Override
    public void caseAExpArg(AExpArg node) {
        returnValue = null;
    }

    @Override
    public void caseANoExpArg(ANoExpArg node) {
        returnValue = null;
    }

    @Override
    public void caseAOuExp(AOuExp node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp1Exp(AToExp1Exp node) {
        returnValue = null;
    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp2Exp1(AToExp2Exp1 node) {
        returnValue = null;
    }

    @Override
    public void caseAEgaleExp2(AEgaleExp2 node) {
        returnValue = null;
    }

    @Override
    public void caseAInfExp2(AInfExp2 node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp3Exp2(AToExp3Exp2 node) {
        returnValue = null;
    }

    @Override
    public void caseAPlusExp3(APlusExp3 node) {
        returnValue = null;
    }

    @Override
    public void caseAMoinsExp3(AMoinsExp3 node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp4Exp3(AToExp4Exp3 node) {
        returnValue = null;
    }

    @Override
    public void caseAMultExp4(AMultExp4 node) {
        returnValue = null;
    }

    @Override
    public void caseADivExp4(ADivExp4 node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp5Exp4(AToExp5Exp4 node) {
        returnValue = null;
    }

    @Override
    public void caseANonExp5(ANonExp5 node) {
        returnValue = null;
    }

    @Override
    public void caseAToExp6Exp5(AToExp6Exp5 node) {
        returnValue = null;
    }

    @Override
    public void caseAExpParExp6(AExpParExp6 node) {
        returnValue = null;
    }

    @Override
    public void caseAConstExp6(AConstExp6 node) {
        returnValue = null;
    }

    @Override
    public void caseAFuncExp6(AFuncExp6 node) {
        returnValue = null;
    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {
        returnValue = null;
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        returnValue = null;
    }

    @Override
    public void caseEOF(EOF node) {

    }

    @Override
    public void caseInvalidToken(InvalidToken node) {

    }
}