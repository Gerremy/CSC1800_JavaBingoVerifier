

public class BingoVerifierJavaMain {
    
    public static void main(String[] args) {
        Machine machine = new Machine();
        machine.readInput();
        machine.marker();
        machine.rotationChecker();
        System.out.print(machine.isItAWinner());
    }
}
