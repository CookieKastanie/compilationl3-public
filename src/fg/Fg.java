package fg;

import nasm.*;
import util.graph.*;

import java.util.*;
import java.io.*;

public class Fg implements NasmVisitor<Void> {
    public Nasm nasm;
    public Graph graph;
    Map<NasmInst, Node> inst2Node;
    Map<Node, NasmInst> node2Inst;
    Map<String, NasmInst> label2Inst;

    public Fg(Nasm nasm) {
        this.nasm = nasm;
        this.inst2Node = new HashMap<NasmInst, Node>();
        this.node2Inst = new HashMap<Node, NasmInst>();
        this.label2Inst = new HashMap<String, NasmInst>();
        this.graph = new Graph();

        for(NasmInst inst : nasm.listeInst) {
            if (inst.label != null) label2Inst.put(inst.label.toString(), inst);
            Node node = graph.newNode();
            inst2Node.put(inst, node);
            node2Inst.put(node, inst);
        }

        for(NasmInst inst : nasm.listeInst) inst.accept(this);
    }

    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".fg";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        for (NasmInst nasmInst : nasm.listeInst) {
            Node n = this.inst2Node.get(nasmInst);
            out.print(n + " : ( ");
            for (NodeList q = n.succ(); q != null; q = q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println(")\t" + nasmInst);
        }
    }

    private void edgeToNext(NasmInst inst) {
        if(nasm.listeInst.indexOf(inst) < nasm.listeInst.size() - 1) {
            graph.addEdge(
                    inst2Node.get(inst),
                    inst2Node.get(nasm.listeInst.get(nasm.listeInst.indexOf(inst) + 1))
            );
        }
    }

    private void edgeToLabel(NasmInst inst) {
        NasmLabel label = (NasmLabel) inst.address;
        if (label2Inst.containsKey(label.val)) {
            graph.addEdge(
                    inst2Node.get(inst),
                    inst2Node.get(label2Inst.get(label.val))
            );
        }
    }

    public Void visit(NasmAdd inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmCall inst) {
        edgeToLabel(inst);
        return null;
    }

    public Void visit(NasmDiv inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJe inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJle inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJne inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmMul inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmOr inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmCmp inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmInst inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJge inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJl inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmNot inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmPop inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmRet inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmXor inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmAnd inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJg inst) {
        edgeToLabel(inst);
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmJmp inst) {
        edgeToLabel(inst);
        return null;
    }

    public Void visit(NasmMov inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmPush inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmSub inst) {
        edgeToNext(inst);
        return null;
    }

    public Void visit(NasmEmpty inst) {
        edgeToNext(inst);
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public Void visit(NasmAddress operand) {
        return null;
    }

    public Void visit(NasmConstant operand) {
        return null;
    }

    public Void visit(NasmLabel operand) {
        return null;
    }

    public Void visit(NasmRegister operand) {
        return null;
    }
}
