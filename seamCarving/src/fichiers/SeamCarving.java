package fichiers;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

/**
 * @author Jofrey Luc
 * @author Quentin Sonrel
 *         Classe content les méthodes (statiques) de Seam Carving
 */
public class SeamCarving {

    /**
     * Lit un fichier PGM (noir et blanc)
     *
     * @param fn Chemin de fichier
     * @return Le tableau de pixels de l'image (niveau de gris)
     */
    public static int[][] readpgm(String fn) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(fn));
            String magic = d.readLine();
            String line = d.readLine();
            while (line.startsWith("#")) {
                line = d.readLine();
            }
            Scanner s = new Scanner(line);
            int width = s.nextInt();
            int height = s.nextInt();
            line = d.readLine();
            s = new Scanner(line);
            int maxVal = s.nextInt();
            int[][] im = new int[height][width];
            s = new Scanner(d);
            int count = 0;
            while (count < height * width) {
                im[count / width][count % width] = s.nextInt();
                count++;
            }
            return im;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Lit un fichier PPM (couleur)
     *
     * @param fn Chemin de fichier
     * @return Le tableau de pixels de l'image (valeurs RGB)
     */
    public static int[][][] readppm(String fn) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(fn));
            String magic = d.readLine();
            String line = d.readLine();
            while (line.
                    startsWith("#")) {
                line = d.readLine();
            }
            Scanner s = new Scanner(line);
            int width = s.nextInt();
            int height = s.nextInt();
            line = d.readLine();
            s = new Scanner(line);
            int maxVal = s.nextInt();
            int[][][] im = new int[height][width][3];
            s = new Scanner(d);
            int count = 0;
            while (count < height * width) {
                im[count / width][count % width][0] = s.nextInt();
                im[count / width][count % width][1] = s.nextInt();
                im[count / width][count % width][2] = s.nextInt();
                count++;
            }
            return im;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Écrit un tableau de pixels (en niveau de gris) dans un fichier PGM (noir et blanc)
     *
     * @param image    Tableau de pixels
     * @param filename Chemin du fichier de sortie
     */
    public static void writepgm(int[][] image, String filename) {
        try {
            FileWriter os = new FileWriter(new File(filename));
            BufferedWriter bw = new BufferedWriter(os);
            String magic = "P2";
            String height = Integer.toString(image[0].length);
            String width = Integer.toString(image.length);
            String maxVal = "255";
            bw.write(magic);
            bw.newLine();
            bw.write(height + " " + width);
            bw.newLine();
            bw.write(maxVal);
            bw.newLine();
            for (int row = 0; row < image.length; row++) {
                for (int col = 0; col < image[0].length; col++) {
                    bw.write(Integer.toString(image[row][col]) + " ");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
            os.close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    /**
     * Écrit un tableau de pixels (en RGB) dans un fichier PPM (couleur)
     *
     * @param image    Tableau de pixels
     * @param filename Chemin du fichier de sortie
     */
    public static void writeppm(int[][][] image, String filename) {
        try {
            FileWriter os = new FileWriter(new File(filename));
            BufferedWriter bw = new BufferedWriter(os);
            String magic = "P3";
            String height = Integer.toString(image[0].length);
            String width = Integer.toString(image.length);
            String maxVal = "255";
            bw.write(magic);
            bw.newLine();
            bw.write(height + " " + width);
            bw.newLine();
            bw.write(maxVal);
            bw.newLine();
            for (int row = 0; row < image.length; row++) {
                for (int col = 0; col < image[0].length; col++) {
                    bw.write(Integer.toString(image[row][col][0]) + " ");
                    bw.write(Integer.toString(image[row][col][1]) + " ");
                    bw.write(Integer.toString(image[row][col][2]) + " ");
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
            os.close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    /**
     * Calcule les valeurs d'intérêt d'un tableau de pixels (noir et blanc)
     *
     * @param image      Tableau de pixels
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     * @return Le tableau d'intérêt
     */
    public static int[][] interest(int[][] image, boolean horizontal) {
        int[][] tabEnd = new int[image.length][image[0].length];
        if (!horizontal) {
            for (int row = 0; row < image.length; row++) {
                tabEnd[row][0] = Math.abs(image[row][0] - image[row][1]);
                for (int col = 1; col < image[0].length - 1; col++) {
                    int voisinGauche = image[row][col - 1];
                    int voisinDroit = image[row][col + 1];
                    tabEnd[row][col] = Math.abs(image[row][col] - (voisinGauche + voisinDroit) / 2);
                }
                tabEnd[row][image[0].length - 1] = Math.abs(image[row][image[0].length - 1] - image[row][image[0].length - 2]);
            }
        } else {
            for (int col = 0; col < image[0].length; col++) {
                tabEnd[0][col] = Math.abs(image[0][col] - image[1][col]);
                for (int row = 1; row < image.length - 1; row++) {
                    int voisinHaut = image[row - 1][col];
                    int voisinBas = image[row + 1][col];
                    tabEnd[row][col] = Math.abs(image[row][col] - (voisinHaut + voisinBas) / 2);
                }
                tabEnd[image.length - 1][col] = Math.abs(image[image.length - 1][col] - image[image.length - 2][col]);
            }
        }
        return tabEnd;
    }

    /**
     * Convertit un tableau de pixels couleur en noir et blanc
     * Sert à calculer les valeurs d'intérêt d'une image couleur
     *
     * @param colored Tableau de pixels couleur
     * @return Le tableau de pixels noir et blanc équivalent
     */
    private static int[][] ppmTopgm(int[][][] colored) {
        int[][] gray = new int[colored.length][colored[0].length];
        for (int i = 0; i < gray.length; i++) {
            for (int j = 0; j < gray[0].length; j++) {
                gray[i][j] = (int) (0.21 * colored[i][j][0] + 0.72 * colored[i][j][1] + 0.07 * colored[i][j][2]);
            }
        }
        return gray;
    }

    /**
     * Convertit un tableau d'intérêt en graphe
     *
     * @param itr        Tableau d'intérêt
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     * @return Le graphe d'intérêt
     */
    public static Graph toGraph(int[][] itr, boolean horizontal) {
        Graph result = new Graph(itr.length * itr[0].length + 2);

        int numero = 1;
        if (!horizontal) {
            for (int i = 0; i < itr[0].length; i++) {
                result.addEdge(new Edge(0, i + 1, 0));
            }
            for (int row = 0; row < itr.length; row++) {
                for (int col = 0; col < itr[0].length; col++) {
                    if (row != itr.length - 1) {
                        if (col != 0) {
                            result.addEdge(new Edge(numero, numero + itr[0].length - 1, itr[row][col]));
                        }
                        result.addEdge(new Edge(numero, numero + itr[0].length, itr[row][col]));
                        if (col != itr[0].length - 1) {
                            result.addEdge(new Edge(numero, numero + itr[0].length + 1, itr[row][col]));
                        }
                        numero++;
                    }
                }
            }
            for (int i = 0; i < itr[0].length; i++) {
                result.addEdge(new Edge(numero, result.vertices() - 1, itr[itr.length - 1][i]));
                numero++;
            }
        } else {
            for (int i = 0; i < itr.length; i++) {
                result.addEdge(new Edge(0, i + 1, 0));
            }
            for (int col = 0; col < itr[0].length; col++) {
                for (int row = 0; row < itr.length; row++) {
                    if (col != itr[0].length - 1) {
                        if (row != 0) {
                            result.addEdge(new Edge(numero, numero + itr.length - 1, itr[row][col]));
                        }
                        result.addEdge(new Edge(numero, numero + itr.length, itr[row][col]));
                        if (row != itr.length - 1) {
                            result.addEdge(new Edge(numero, numero + itr.length + 1, itr[row][col]));
                        }
                        numero++;
                    }
                }
            }
            for (int i = 0; i < itr.length; i++) {
                result.addEdge(new Edge(numero, result.vertices() - 1, itr[itr.length - 1][i]));
                numero++;
            }
        }

        return result;
    }


    /**
     * Convertit un tableau d'intérêt en graphe - version Suurballe
     *
     * @param itr Tableau d'intérêt
     * @return Le graphe d'intérêt
     */
    public static Graph toGraphSuur(int[][] itr) {
        int newLength = (itr.length - 2) * 2 + 2;

        Graph result = new Graph(newLength * itr[0].length + 2);

        int numero = 1;
        for (int i = 0; i < itr[0].length; i++) {
            result.addEdge(new Edge(0, i + 1, 0));
        }

        boolean edgeAtZero = false;
        int shift = 0;

        for (int row = 0; row < newLength; row++) {
            if (row != newLength - 1) {
                if (!edgeAtZero) {
                    for (int col = 0; col < itr[0].length; col++) {
                        if (col != 0) {
                            result.addEdge(new Edge(numero, numero + itr[0].length - 1, itr[row - shift][col]));
                        }
                        result.addEdge(new Edge(numero, numero + itr[0].length, itr[row - shift][col]));
                        if (col != itr[0].length - 1) {
                            result.addEdge(new Edge(numero, numero + itr[0].length + 1, itr[row - shift][col]));
                        }
                        numero++;
                    }
                    edgeAtZero = true;

                } else {
                    for (int col = 0; col < itr[0].length; col++) {
                        result.addEdge(new Edge(numero, numero + itr[0].length, 0));
                        numero++;
                    }
                    shift++;
                    edgeAtZero = false;
                }
            }
        }
        for (int i = 0; i < itr[0].length; i++) {
            result.addEdge(new Edge(numero, result.vertices() - 1, itr[itr.length - 1][i]));
            numero++;
        }
        return result;
    }

    /**
     * Recherche le plus court chemin (d'un sommet s à un sommet t) dans un graphe d'intérêt
     * Concrètement : renvoie la ligne ou la colonne de sommets (et donc pixels) les moins intéressants (et donc à supprimer)
     *
     * @param g Graphe d'intérêt
     * @param s Sommet s (de départ)
     * @param t Sommet t (d'arrivée)
     * @return Tableau des sommets les moins intéressants
     */
    public static ArrayList<Integer> dijkstra(Graph g, int s, int t) {
        int N = g.vertices();
        Heap h = new Heap(N);

        h.decreaseKey(s, Integer.MIN_VALUE);

        int[] parents = new int[N];
        for (int j = 0; j < N; j++) {
            parents[j] = -1;
        }

        while (!h.isEmpty()) {
            int min = h.pop();
            for (Edge e : g.next(min)) {
                if (h.priority(e.to) > h.priority(min) + e.cost) {
                    h.decreaseKey(e.to, h.priority(min) + e.cost);
                    parents[e.to] = min;
                }
            }
        }

        ArrayList<Integer> li = new ArrayList<>();
        int current = t;
        while (current != s) {
            li.add(new Integer(current));
            current = parents[current];
        }

        Collections.reverse(li);

        return li;
    }

    /**
     * Recherche le plus court chemin (d'un sommet s à un sommet t) dans un graphe d'intérêt
     * Concrètement : renvoie la ligne ou la colonne de sommets (et donc pixels) les moins intéressants (et donc à supprimer)
     * De plus, cette version remplit aussi un tableau d'entiers passés en paramètres avec les coûts des chemins minimaux pour
     * chaque sommet
     *
     * @param g     Graphe d'intérêt
     * @param s     Sommet s (de départ)
     * @param t     Sommet t (d'arrivée)
     * @param costs Tableau des coûts des sommets
     * @return Tableau des sommets les moins intéressants
     */
    public static ArrayList<Integer> dijkstra(Graph g, int s, int t, int[] costs) {
        int N = g.vertices();
        Heap h = new Heap(N);

        h.decreaseKey(s, Integer.MIN_VALUE);

        int[] parents = new int[N];
        for (int j = 0; j < N; j++) {
            parents[j] = -1;
        }

        while (!h.isEmpty()) {
            int min = h.pop();
            costs[min] = h.priority(min);
            for (Edge e : g.next(min)) {
                if (h.priority(e.to) > h.priority(min) + e.cost) {
                    h.decreaseKey(e.to, h.priority(min) + e.cost);
                    parents[e.to] = min;
                }
            }
        }

        ArrayList<Integer> li = new ArrayList<>();
        int current = t;
        while (current != s) {
            li.add(new Integer(current));
            current = parents[current];
        }

        Collections.reverse(li);

        return li;
    }

    /**
     * Calcule les deux plus courts chemins en utilisant la méthode de Suurballe
     *
     * @param g Graphe de base (modifié pour dédoubler les sommets)
     * @param s Sommet de départ des deux chemins
     * @param t Sommet d'arrivée des deux chemins
     * @return Une liste contenant les deux plus courts chemins, mélangés mais normalement c'est pas grave puisqu'on parcourt tout
     */

    public static ArrayList<Integer> twopath(Graph g, int s, int t, int imgWidth) {
        int[] verticesCosts = new int[g.vertices()];

        ArrayList<Integer> firstShortest = dijkstra(g, s, t, verticesCosts);

        Graph graphReverted = addAndRevert(g, firstShortest, verticesCosts);

        ArrayList<Integer> secondShortest = dijkstra(graphReverted, s, t);

        firstShortest = removeInArrayList(firstShortest, imgWidth);
        secondShortest = removeInArrayList(secondShortest, imgWidth);

        for (Integer i : secondShortest) {
            if (!firstShortest.contains(i)) {
                firstShortest.add(i);
            }
        }

        return firstShortest;
    }

    private static ArrayList<Integer> removeInArrayList(ArrayList<Integer> list, int imgWidth) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                result.add(list.get(i));
            } else if (i % 2 != 0) {
                result.add(list.get(i));
            }
        }

        return result;
    }

    /**
     * Change les poids des arêtes et en inverse certaines suivant le début de la méthode de Suurballe
     *
     * @param g        Graphe de base (modifié pour dédoubler les sommets)
     * @param shortest Chemin le plus court (liste de sommets)
     * @return Un graphe correspondant à g modifié
     */
    private static Graph addAndRevert(Graph g, ArrayList<Integer> shortest, int[] verticesCosts) {
        Graph gReverted = new Graph(g.vertices());
        for (Edge e : g.edges()) {
            gReverted.addEdge(new Edge(e.from, e.to, e.cost));
        }

        int pathToU = 0, pathToV = 0;
        for (Edge e : gReverted.edges()) {
            e.cost += (verticesCosts[e.from] - verticesCosts[e.to]);
        }

        shortest.add(new Integer(0));

        for (Edge e : gReverted.edges()) {
            if (shortest.contains(e.to) && shortest.contains(e.from)) {
                e.to += e.from;
                e.from = e.to - e.from;
                e.to -= e.from;
            }
        }

        shortest.remove(shortest.size() - 1);

        return gReverted;
    }

    /**
     * Supprime d'un tableau de pixels (noir et blanc) les moins intéréssants
     *
     * @param base       Tableau de pixels
     * @param rm         Tableau de sommets (numéros de pixels) retirés
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     * @return Le nouveau tableau de pixels (débarrassé de ceux à supprimer)
     */
    private static int[][] removeInpgm(int[][] base, ArrayList<Integer> rm, boolean horizontal) {
        int k = 0;
        int newpgm[][];

        //Ci-gît mon âme, morte pour ce projet. --Q.

        int nbSommet = 1;

        if (!horizontal) {
            newpgm = new int[base.length][base[0].length - 1];
            for (int i = 0; i < base.length; i++) {
                for (int j = 0; j < base[i].length; j++) {
                    if (!rm.contains(nbSommet)) {
                        newpgm[i][k] = base[i][j];
                        k++;
                    }
                    nbSommet++;
                }
                k = 0;
            }
        } else {
            newpgm = new int[base.length - 1][base[0].length];
            for (int i = 0; i < base[0].length; i++) {
                for (int j = 0; j < base.length; j++) {
                    if (!rm.contains(nbSommet)) {
                        newpgm[k][i] = base[j][i];
                        k++;
                    }
                    nbSommet++;
                }
                k = 0;
            }
        }

        return newpgm;
    }

    /**
     * Supprime d'un tableau de pixels (noir et blanc) les moins intéréssants - pour la méthode 2
     *
     * @param base       Tableau de pixels
     * @param rm         Tableau de sommets (numéros de pixels) retirés
     * @return Le nouveau tableau de pixels (débarrassé de ceux à supprimer)
     */
    private static int[][] removeInpgm2(int[][] base, ArrayList<Integer> rm) {
        int k = 0;
        int newpgm[][];

        int nbSommet = 1;

        newpgm = new int[base.length][base[0].length - 2];
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                if (!rm.contains(nbSommet)) {
                    newpgm[i][k] = base[i][j];
                    k++;
                }
                nbSommet++;
            }
            k = 0;
            if (i != 0) {
                nbSommet += base[0].length;
            }
        }

        return newpgm;
    }


    /**
     * Supprime d'un tableau de pixels (couleur) les moins intéréssants
     *
     * @param base       Tableau de pixel
     * @param rm         Tableau de sommets (numéros de pixels) retirer
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     * @return Le nouveau tableau de pixels (débarrassé de ceux à supprimer)
     */
    private static int[][][] removeInppm(int[][][] base, ArrayList<Integer> rm, boolean horizontal) {
        int k = 0;
        int newppm[][][];

        int nbSommet = 1;

        if (!horizontal) {
            newppm = new int[base.length][base[0].length - 1][3];
            for (int i = 0; i < base.length; i++) {
                for (int j = 0; j < base[i].length; j++) {
                    if (!rm.contains(nbSommet)) {
                        newppm[i][k][0] = base[i][j][0];
                        newppm[i][k][1] = base[i][j][1];
                        newppm[i][k][2] = base[i][j][2];
                        k++;
                    }
                    nbSommet++;
                }
                k = 0;
            }
        } else {
            newppm = new int[base.length - 1][base[0].length][3];
            for (int i = 0; i < base[0].length; i++) {
                for (int j = 0; j < base.length; j++) {
                    if (!rm.contains(nbSommet)) {
                        newppm[i][k][0] = base[i][j][0];
                        newppm[i][k][1] = base[i][j][1];
                        newppm[i][k][2] = base[i][j][2];
                        k++;
                    }
                    nbSommet++;
                }
                k = 0;
            }
        }

        return newppm;
    }

    /**
     * Supprime d'un tableau de pixels (couleur) les moins intéréssants - pour la méthode 2
     *
     * @param base       Tableau de pixel
     * @param rm         Tableau de sommets (numéros de pixels) retirer
     * @return Le nouveau tableau de pixels (débarrassé de ceux à supprimer)
     */
    private static int[][][] removeInppm2(int[][][] base, ArrayList<Integer> rm) {
        int k = 0;
        int newppm[][][];

        int nbSommet = 1;

        newppm = new int[base.length][base[0].length - 2][3];
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                if (!rm.contains(nbSommet)) {
                    newppm[i][k][0] = base[i][j][0];
                    newppm[i][k][1] = base[i][j][1];
                    newppm[i][k][2] = base[i][j][2];
                    k++;
                }
                nbSommet++;
            }
            k = 0;
            if (i != 0) {
                nbSommet += base[0].length;
            }
        }

        return newppm;
    }

    /**
     * Applique la méthode de Seam Carving à une image
     *
     * @param p          Chemin de l'image à traiter (PGM ou PPM)
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carve(String p, int howmany, String dest, boolean horizontal) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(p));
            String magic = d.readLine();
            if (magic.equals("P2")) {
                int[][] img = readpgm(p);
                carvePgm(img, howmany, dest, horizontal);
            } else if (magic.equals("P3")) {
                int[][][] img = readppm(p);
                carvePpm(img, howmany, dest, horizontal);
            } else {
                System.out.println("Impossible de charger le fichier");
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    /**
     * Applique la méthode de Seam Carving de la partie 2 du sujet à une image
     *
     * @param p          Chemin de l'image à traiter (PGM ou PPM)
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carve2(String p, int howmany, String dest, boolean horizontal) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(p));
            String magic = d.readLine();
            if (magic.equals("P2")) {
                int[][] img = readpgm(p);
                carvePgm2(img, howmany/2, dest, horizontal);
            } else if (magic.equals("P3")) {
                int[][][] img = readppm(p);
                carvePpm2(img, howmany/2, dest, horizontal);
            } else {
                System.out.println("Impossible de charger le fichier");
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    /**
     * Méthode de Seam Carving pour un fichier PGM (noir et blanc)
     * Appelée automatiquement par la méthode Seam selon le format de fichier
     *
     * @param baseImg    Tableau de pixels de l'image à traiter
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carvePgm(int[][] baseImg, int howmany, String dest, boolean horizontal) {
        for (int i = 0; i < howmany; i++) {
            int[][] intrs = interest(baseImg, horizontal);
            Graph gr = toGraph(intrs, horizontal);
            ArrayList<Integer> d = dijkstra(gr, 0, gr.vertices() - 1);
            baseImg = removeInpgm(baseImg, d, horizontal);
            System.out.print(".");
        }
        writepgm(baseImg, dest + ".pgm");
        System.out.println("Terminé !");
    }

    /**
     * Méthode de Seam Carving de la partie 2 du sujet pour un fichier PGM (noir et blanc)
     * Appelée automatiquement par la méthode Seam selon le format de fichier
     *
     * @param baseImg    Tableau de pixels de l'image à traiter
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carvePgm2(int[][] baseImg, int howmany, String dest, boolean horizontal) {
        for (int i = 0; i < howmany; i++) {
            int[][] itrs = interest(baseImg, horizontal);
            Graph gr = toGraphSuur(itrs);
            ArrayList<Integer> d = twopath(gr, 0, gr.vertices() - 1, baseImg[0].length);
            baseImg = removeInpgm2(baseImg, d);
            System.out.print(".");
        }
        writepgm(baseImg, dest + ".pgm");
        System.out.println("Terminé !");
    }

    /**
     * Méthode de Seam Carving pour un fichier PPM (couleur)
     * Appelée automatiquement par la méthode Seam selon le format de fichier
     *
     * @param baseImg    Tableau de pixels de l'image à traiter
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carvePpm(int[][][] baseImg, int howmany, String dest, boolean horizontal) {
        for (int i = 0; i < howmany; i++) {
            int[][] intrs = interest(ppmTopgm(baseImg), horizontal);
            Graph gr = toGraph(intrs, horizontal);
            ArrayList<Integer> d = dijkstra(gr, 0, gr.vertices() - 1);
            baseImg = removeInppm(baseImg, d, horizontal);
            System.out.print(".");
        }
        writeppm(baseImg, dest + ".ppm");
        System.out.println("Terminé !");
    }

    /**
     * Méthode de Seam Carving de la partie 2 du sujet pour un fichier PPM (couleur)
     * Appelée automatiquement par la méthode Seam selon le format de fichier
     *
     * @param baseImg    Tableau de pixels de l'image à traiter
     * @param howmany    Nombre de pixels à supprimer
     * @param dest       Fichier de sortie de l'image résultat (sans extension)
     * @param horizontal Sens de traitement (pour une réduction en lignes ou en colonnes)
     */
    public static void carvePpm2(int[][][] baseImg, int howmany, String dest, boolean horizontal) {
        for (int i = 0; i < howmany; i++) {
            int[][] itrs = interest(ppmTopgm(baseImg), horizontal);
            Graph gr = toGraphSuur(itrs);
            ArrayList<Integer> d = twopath(gr, 0, gr.vertices() - 1, baseImg[0].length);
            baseImg = removeInppm2(baseImg, d);
            System.out.print(".");
        }
        writeppm(baseImg, dest + ".ppm");
        System.out.println("Terminé");
    }

    /**
     * Retourne les dimensions d'une image PGM ou PPM
     *
     * @param p Chemin de l'image
     * @return Les dimensions de l'image (largeur, hauteur)
     */
    public static int[] getDims(String p) {
        try {
            BufferedReader d = new BufferedReader(new FileReader(p));
            String magic = d.readLine();
            String line = d.readLine();
            while (line.startsWith("#")) {
                line = d.readLine();
            }
            Scanner s = new Scanner(line);
            int width = s.nextInt();
            int height = s.nextInt();
            int[] tab = {width, height};
            return tab;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return null;
        }
    }
}