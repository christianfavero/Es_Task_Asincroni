import java.util.concurrent.*;
import java.util.*;

class ProdottoScalareTask implements Callable<Double> {
    private int[] u;
    private int[] v;

    public ProdottoScalareTask(int[] u, int[] v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public Double call() {
        double prodotto = 0;
        for (int i = 0; i < u.length; i++) {
            prodotto += u[i] * v[i];
        }
        return prodotto;
    }
}

class ModuloTask implements Callable<Double> {
    private int[] vettore;

    public ModuloTask(int[] vettore) {
        this.vettore = vettore;
    }

    @Override
    public Double call() {
        double somma = 0;
        for (int x : vettore) {
            somma += x * x;
        }
        return Math.sqrt(somma);
    }
}


public class Main {
    public static void main(String[] args) throws Exception {

        int[] U = {1, 2, 3};
        int[] V = {4, 5, 6};

        if (U.length != V.length) {
            System.out.println("I vettori devono avere la stessa dimensione");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<Double> prodottoScalareFuture =
                executor.submit(new ProdottoScalareTask(U, V));

        Future<Double> moduloUFuture =
                executor.submit(new ModuloTask(U));

        Future<Double> moduloVFuture =
                executor.submit(new ModuloTask(V));

        double prodottoScalare = prodottoScalareFuture.get();
        double moduloU = moduloUFuture.get();
        double moduloV = moduloVFuture.get();

        executor.shutdown();

        double cosAlfa = prodottoScalare / (moduloU * moduloV);
        double alfa = Math.acos(cosAlfa);
        double alfaGradi = Math.toDegrees(alfa);

        System.out.println("Prodotto scalare: " + prodottoScalare);
        System.out.println("|U| = " + moduloU);
        System.out.println("|V| = " + moduloV);
        System.out.println("Angolo (radianti): " + alfa);
        System.out.println("Angolo (gradi): " + alfaGradi);
    }
}
