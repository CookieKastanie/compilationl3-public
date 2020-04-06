package fg;

import util.graph.*;
import nasm.*;
import util.intset.*;

import java.io.*;
import java.util.*;

public class FgSolution {
    int iterNum = 0;
    public Nasm nasm;
    Fg fg;
    public Map<NasmInst, IntSet> use;
    public Map<NasmInst, IntSet> def;
    public Map<NasmInst, IntSet> in;
    public Map<NasmInst, IntSet> out;

    public Map<NasmInst, IntSet> inTemp;
    public Map<NasmInst, IntSet> outTemp;

    public FgSolution(Nasm nasm, Fg fg) {
        this.nasm = nasm;
        this.fg = fg;
        this.use = new HashMap<NasmInst, IntSet>();
        this.def = new HashMap<NasmInst, IntSet>();
        this.in = new HashMap<NasmInst, IntSet>();
        this.out = new HashMap<NasmInst, IntSet>();

        this.inTemp = new HashMap<NasmInst, IntSet>();
        this.outTemp = new HashMap<NasmInst, IntSet>();

        calc();
    }

    private void init(NasmInst inst) {
        in.put(inst, new IntSet(nasm.getTempCounter()));
        out.put(inst, new IntSet(nasm.getTempCounter()));

        inTemp.put(inst, new IntSet(nasm.getTempCounter()));
        outTemp.put(inst, new IntSet(nasm.getTempCounter()));

        //////////////////////////////////////////////////////////////////////////

        IntSet defSet = new IntSet(nasm.getTempCounter());

        if(inst.srcDef) addRegister(inst.source, defSet);
        if(inst.destDef) addRegister(inst.destination, defSet);

        def.put(inst, defSet);

        IntSet useSet = new IntSet(nasm.getTempCounter());

        if(inst.srcUse) addRegister(inst.source, useSet);
        if(inst.destUse) addRegister(inst.destination, useSet);

        use.put(inst, useSet);
    }

    private void addRegister(NasmOperand ope, IntSet intSet) {
        if(ope.isGeneralRegister()) intSet.add(((NasmRegister) ope).val);

        if(ope instanceof NasmAddress) {
            NasmAddress adr = (NasmAddress) ope;

            if(adr.base.isGeneralRegister()) {
                intSet.add(((NasmRegister) adr.base).val);
            }

            if(adr.offset != null && adr.offset.isGeneralRegister()) {
                intSet.add(((NasmRegister) adr.offset).val);
            }
        }
    }

    public boolean verif() {
        for(NasmInst inst : nasm.listeInst) {
            if(!(inTemp.get(inst).equal(in.get(inst)) && outTemp.get(inst).equal(out.get(inst)))) return true;
        }

        return false;
    }

    // Algorithme de calcul de in et out
    private void calc() {
        for(NasmInst inst : nasm.listeInst) init(inst);

        do {
            for(NasmInst inst : nasm.listeInst) {
                inTemp.replace(inst, in.get(inst).copy());
                outTemp.replace(inst, out.get(inst).copy());

                in.replace(inst, out.get(inst).copy().minus(def.get(inst)).union(use.get(inst)));
                for(NodeList list = fg.inst2Node.get(inst).pred(); list != null; list = list.tail) {
                    out.get(fg.node2Inst.get(list.head)).union(in.get(inst));
                }
            }

            ++iterNum;

        } while(verif());
    }

    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".fgs";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        out.println("iter num = " + iterNum);
        for (NasmInst nasmInst : this.nasm.listeInst) {
            out.println("use = " + this.use.get(nasmInst) + " def = " + this.def.get(nasmInst) + "\tin = " + this.in.get(nasmInst) + "\t \tout = " + this.out.get(nasmInst) + "\t \t" + nasmInst);
        }
    }
}

    
