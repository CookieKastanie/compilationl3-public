package ig;

import fg.*;
import nasm.*;
import util.graph.*;
import util.intset.*;

import java.util.*;
import java.io.*;

public class Ig {
    public Graph graph;
    public FgSolution fgs;
    public int regNb;
    public Nasm nasm;
    public Node int2Node[];


    public Ig(FgSolution fgs) {
        this.fgs = fgs;
        this.graph = new Graph();
        this.nasm = fgs.nasm;
        this.regNb = this.nasm.getTempCounter();
        this.int2Node = new Node[regNb];
        this.build();

        this.build();
        colorGraph = new ColorGraph(graph, regNb, getPrecoloredTemporaries());

        allocateRegisters();
    }

    // Algorithm 1
    public void build() {
        for(int i = 0; i < regNb; ++i) int2Node[i] = graph.newNode();

        for(NasmInst inst : nasm.listeInst) {
            IntSet in = fgs.in.get(inst);
            IntSet out = fgs.out.get(inst);

            for(int r = 0 ; r < regNb; ++r) {
                //in
                if(in.isMember(r)) {
                    for(int rp = 0 ; rp < regNb; ++rp) {
                        if(in.isMember(rp) && r != rp) {
                            graph.addNOEdge(int2Node[r], int2Node[rp]);
                        }
                    }
                }

                //out
                if(out.isMember(r)) {
                    for(int rp = 0 ; rp < regNb; ++rp) {
                        if(out.isMember(rp) && r != rp) {
                            graph.addNOEdge(int2Node[r], int2Node[rp]);
                        }
                    }
                }
            }
        }
    }

    public int[] getPrecoloredTemporaries() {
        int[] colors = new int[regNb];

        for(NasmInst inst : nasm.listeInst) {
            if(inst.source != null) {

            }
            colors[(NasmOperand) inst.source];
        }

        return colors;
    }

    private int getColor(NasmOperand ope) {
        if(ope.isGeneralRegister()) return ((NasmRegister) ope).color;

        if(ope instanceof NasmAddress) {
            NasmAddress address = (NasmAddress) ope;

            if (address.base.isGeneralRegister()) {
                return ((NasmRegister) address.base).color;
            }

            if (address.offset != null && address.offset.isGeneralRegister()) {
                return ((NasmRegister) address.offset).color;
            }
        }
    }


    public void allocateRegisters() {
    }


    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".ig";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        for (int i = 0; i < regNb; i++) {
            Node n = this.int2Node[i];
            out.print(n + " : ( ");
            for (NodeList q = n.succ(); q != null; q = q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println(")");
        }
    }
}
