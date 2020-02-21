package ts;

import sa.*;

public class Sa2ts<T> extends SaDepthFirstVisitor<T> {

    private final static int GLOBAL = 0;
    private final static int LOCAL = 1;
    private final static int ARG = 2;

    private Ts tableGlobal;
    private Ts tableLocal;

    private int context;

    public Sa2ts(SaNode root) {
        tableGlobal = new Ts();
        tableLocal = null;

        context = GLOBAL;

        root.accept(this);
    }

    public Ts getTableGlobale() {
        return tableGlobal;
    }

    public T visit(SaDecVar node) {
        defaultIn(node);

        TsItemVar tsItemVar = null;

        if(context == GLOBAL) {

            if(tableGlobal.getVar(node.getNom()) != null) {
                System.out.println("ERREUR DEC VAR");
                System.exit(1);
            } else {
                tsItemVar = tableGlobal.addVar(node.getNom(), 1);
            }

        } else {

            if(tableLocal.getVar(node.getNom()) != null) {
                System.out.println("ERREUR DEC VAR");
                System.exit(1);
            } else {

                if(context == ARG) {
                    tsItemVar = tableLocal.addParam(node.getNom());
                } else {
                    tsItemVar = tableLocal.addVar(node.getNom(), 1);
                }
            }
        }

        node.tsItem = tsItemVar;

        defaultOut(node);
        return null;
    }

    public T visit(SaDecTab node){
        defaultIn(node);

        TsItemVar tsItemVar = null;

        if(context == GLOBAL) {
            if(tableGlobal.getVar(node.getNom()) != null) {
                System.out.println("ERREUR DEC TAB");
                System.exit(1);
            } else {
                tsItemVar = tableGlobal.addVar(node.getNom(), node.getTaille());
            }

        } else {
            System.out.println("ERREUR DEC TAB");
            System.exit(1);
        }

        node.tsItem = tsItemVar;

        defaultOut(node);
        return null;
    }

    public T visit(SaDecFonc node) {
        defaultIn(node);

        Ts table = new Ts();
        TsItemFct tsItemFct = tableGlobal.addFct(node.getNom(), node.getParametres() != null ? node.getParametres().length() : 0, table, node);
        node.tsItem = tsItemFct;

        tableLocal = table;

        context = ARG;

        if(node.getParametres() != null) node.getParametres().accept(this);

        context = LOCAL;

        if(node.getVariable() != null) node.getVariable().accept(this);

        node.getCorps().accept(this);

        context = GLOBAL;

        defaultOut(node);
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public T visit(SaVarSimple node) {
        defaultIn(node);

        TsItemVar gVar = tableLocal.getVar(node.getNom());
        TsItemVar lVar = tableGlobal.getVar(node.getNom());

        if(gVar == null && lVar == null) {
            System.out.println("ERREUR VAR");
            System.exit(1);
        }

        if (gVar != null) node.tsItem = gVar;

        if (lVar != null) node.tsItem = lVar;

        defaultOut(node);
        return null;
    }

    public T visit(SaVarIndicee node) {
        defaultIn(node);
        node.getIndice().accept(this);

        TsItemVar gVar = tableLocal.getVar(node.getNom());
        TsItemVar lVar = tableGlobal.getVar(node.getNom());

        if((gVar == null && lVar == null) || node.getIndice() == null) {
            System.out.println("ERREUR VAR INDICEE");
            System.exit(1);
        }

        if (gVar != null) node.tsItem = gVar;

        if (lVar != null) node.tsItem = lVar;

        defaultOut(node);
        return null;
    }

    public T visit(SaAppel node) {
        defaultIn(node);
        if(node.getArguments() != null) node.getArguments().accept(this);

        TsItemFct gFunc = tableLocal.getFct(node.getNom());
        TsItemFct lFunc = tableGlobal.getFct(node.getNom());

        final int argLength = node.getArguments() != null ? node.getArguments().length() : 0;
        if((gFunc == null || gFunc.getNbArgs() != argLength) && (lFunc == null || lFunc.getNbArgs() != argLength)) {
            System.out.println("ERREUR FUNC");
            System.exit(1);
        }

        if (gFunc != null) node.tsItem = gFunc;

        if (lFunc != null) node.tsItem = lFunc;

        defaultOut(node);
        return null;
    }
}
