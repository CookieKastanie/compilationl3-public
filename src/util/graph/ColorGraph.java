package util.graph;

import util.graph.*;
import util.intset.*;

import java.util.*;
import java.io.*;

public class ColorGraph {
    public Graph G;
    public int R;
    public int K;
    private Stack<Integer> pile;
    public IntSet enleves;
    public IntSet deborde;
    public int[] couleur;
    public Node[] int2Node;
    static int NOCOLOR = -1;
    private int nbPreColor;

    public ColorGraph(Graph G, int K, int[] phi) {
        this.G = G;
        this.K = K;
        pile = new Stack<>();
        R = G.nodeCount();
        couleur = new int[R];
        enleves = new IntSet(R);
        deborde = new IntSet(R);
        int2Node = G.nodeArray();

        nbPreColor = 0;
        for (int v = 0; v < R; v++) {
            int preColor = phi[v];
            if(preColor >= 0 && preColor < K){
                couleur[v] = phi[v];
                ++nbPreColor;
            }
            else{
                couleur[v] = NOCOLOR;
            }
        }
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* associe une couleur à tous les sommets se trouvant dans la pile */
    /*-------------------------------------------------------------------------------------------------------------*/

    // Algorithm 4
    public void selection() {
        while(!pile.isEmpty()) {
            int s = pile.pop();
            IntSet c = couleursVoisins(s);
            /*if(c.getNbElements() != K) {
                couleur[s] = choisisCouleur(c);
            }*/
            if(couleur[s] == NOCOLOR) {
                couleur[s] = choisisCouleur(c);
            }
        }
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* récupère les couleurs des voisins de t */
    /*-------------------------------------------------------------------------------------------------------------*/

    public IntSet couleursVoisins(int t) {
        IntSet colorSet = new IntSet(K);
        for(NodeList list = int2Node[t].succ(); list != null; list = list.tail) {
            Node head = list.head;
            if(couleur[head.mykey] != NOCOLOR) {
                colorSet.add(couleur[head.mykey]);
            }
        }

        return colorSet;
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* recherche une couleur absente de colorSet */
    /*-------------------------------------------------------------------------------------------------------------*/

    public int choisisCouleur(IntSet colorSet) {
        for(int i = 0; i < colorSet.getSize(); ++i) {
            if(!colorSet.isMember(i)) return i;
        }

        return NOCOLOR;
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* calcule le nombre de voisins du sommet t */
    /*-------------------------------------------------------------------------------------------------------------*/

    public int nbVoisins(int t) {
        int nbVoisins = 0;
        for(NodeList list = int2Node[t].succ(); list != null; list = list.tail) {
            if(!enleves.isMember(list.head.mykey)) ++nbVoisins;
        }

        return nbVoisins;
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* simplifie le graphe d'interférence g                                                                        */
    /* la simplification consiste à enlever du graphe les temporaires qui ont moins de k voisins                   */
    /* et à les mettre dans une pile                                                                               */
    /* à la fin du processus, le graphe peut ne pas être vide, il s'agit des temporaires qui ont au moins k voisin */
    /*-------------------------------------------------------------------------------------------------------------*/

    // Algorithm 2
    public void simplification() {
        int n = R - nbPreColor;
        boolean modif = true;
        while(pile.size() != n && modif) {
            modif = false;
            for(int s = 0; s < R; ++s) {
                if (!enleves.isMember(s) && nbVoisins(s) < K && couleur[s] == NOCOLOR) {
                    pile.push(s);
                    enleves.add(s);
                    modif = true;
                }
            }
        }
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------------------------*/

    // Algorithm 3
    public void debordement() {
        while(pile.size() != R) {
            int s = choisisSommet();
            pile.push(s);
            enleves.add(s);
            deborde.add(s);
            simplification();
        }
    }

    private int choisisSommet() {
        for(int i = 0; i < R; ++i) {
            if(!pile.contains(i)) return i;
        }

        return -1;
    }


    /*-------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------------------------*/

    public void coloration() {
        this.simplification();
        this.debordement();
        this.selection();
    }

    void affiche() {
        System.out.println("vertex\tcolor");
        for (int i = 0; i < R; i++) {
            System.out.println(i + "\t" + couleur[i]);
        }
    }
}
