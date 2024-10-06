package functionalClass;

import java.util.List;
import java.util.function.BiConsumer;

public interface BruteForceListener {
    void startBruteForce(List<String> plaintexts, List<String> ciphertexts, List<Boolean> useFlags, BiConsumer<String, String> uiUpdater);
}