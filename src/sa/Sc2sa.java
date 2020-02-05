package sa;

import sc.analysis.Analysis;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {
    private SaNode returnValue;

    @Override
    public void caseAProgrammeProgramme(AProgrammeProgramme node) {
        SaLDec variables = null;
        SaLDec fonctions = null;

        if(node.getDecVarGlob() != null) {
            node.getDecVarGlob().apply(this);
            variables = (SaLDec) returnValue;
        }

        if(node.getDecListFonc() != null) {
            node.getDecListFonc().apply(this);
            fonctions = (SaLDec) returnValue;
        }

        returnValue = new SaProg(variables, fonctions);
    }

    @Override
    public void caseAVarGlobDecVarGlob(AVarGlobDecVarGlob node) {

    }

    @Override
    public void caseANoVarGlobDecVarGlob(ANoVarGlobDecVarGlob node) {

    }

    @Override
    public void caseAVarDecListVar(AVarDecListVar node) {

    }

    @Override
    public void caseAListVarDecListVar(AListVarDecListVar node) {

    }

    @Override
    public void caseANoVarDecListVar(ANoVarDecListVar node) {

    }

    @Override
    public void caseAEntierDecVar(AEntierDecVar node) {

    }

    @Override
    public void caseATabDecVar(ATabDecVar node) {

    }

    @Override
    public void caseAListFoncDecListFonc(AListFoncDecListFonc node) {

    }

    @Override
    public void caseANoFoncDecListFonc(ANoFoncDecListFonc node) {

    }

    @Override
    public void caseADecFoncDecFonc(ADecFoncDecFonc node) {

    }

    @Override
    public void caseADecListFoncOptionDecListFoncOption(ADecListFoncOptionDecListFoncOption node) {

    }

    @Override
    public void caseANoDecListFoncOptionDecListFoncOption(ANoDecListFoncOptionDecListFoncOption node) {

    }

    @Override
    public void caseABlockBloc(ABlockBloc node) {

    }

    @Override
    public void caseAInstInstList(AInstInstList node) {

    }

    @Override
    public void caseANoInstInstList(ANoInstInstList node) {

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
    public void caseAFoncInst(AFoncInst node) {

    }

    @Override
    public void caseAEcrireInst(AEcrireInst node) {

    }

    @Override
    public void caseAAffIAff(AAffIAff node) {

    }

    @Override
    public void caseASiISi(ASiISi node) {

    }

    @Override
    public void caseASinonISinon(ASinonISinon node) {

    }

    @Override
    public void caseANoSinonISinon(ANoSinonISinon node) {

    }

    @Override
    public void caseATantqueITantque(ATantqueITantque node) {

    }

    @Override
    public void caseARetourIRetour(ARetourIRetour node) {

    }

    @Override
    public void caseAAppFoncIFonc(AAppFoncIFonc node) {

    }

    @Override
    public void caseAAppLireIFonc(AAppLireIFonc node) {

    }

    @Override
    public void caseAAppEcrireIEcrire(AAppEcrireIEcrire node) {

    }

    @Override
    public void caseAIdentVarVar(AIdentVarVar node) {

    }

    @Override
    public void caseAIdentTabVar(AIdentTabVar node) {

    }

    @Override
    public void caseAFoncFonc(AFoncFonc node) {

    }

    @Override
    public void caseAArgsArgs(AArgsArgs node) {

    }

    @Override
    public void caseANoArgsArgs(ANoArgsArgs node) {

    }

    @Override
    public void caseAArgArg(AArgArg node) {

    }

    @Override
    public void caseANoArgArg(ANoArgArg node) {

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
    public void caseAFoncExp6(AFoncExp6 node) {

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