import expression.*;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by heat_wave on 10/6/15.
 */
public class Task3 {
    public static void main(String[] args) {
        Proofs.parseProofs();
        Axioms.parseAxioms();
        new Task3().solve();
    }
    public void solve() {
        try (Scanner in = new Scanner(new File("task3.in"))) {
            Expression expression = Parser.parse(in.next().replace("->", ">"));
            Prover.prove(expression);
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
