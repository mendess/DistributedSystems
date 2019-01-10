class ShuttleSession implements Runnable {
    private Controlador c;
    private int posicao = 0;

    public ShuttleSession(Controlador c) {
        this.c = c;
    }

    public void run() {
        for(;;){
            c.partida();
            System.out.println("Parti de " + posicao);
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException ignored) {
            }
            c.chegada();
            posicao = (posicao + 1) % 4;
            System.out.println("Cheguei a " + posicao);
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ignored) {
            }
        }
    }
}

