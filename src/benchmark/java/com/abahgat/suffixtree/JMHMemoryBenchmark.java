package com.abahgat.suffixtree;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JMHMemoryBenchmark {

    @Param({ "100000", "500000" })
    public int wordCount;

    @Param({ "10" })
    public int wordLength;

    @Param({ "random", "repeated" })
    public String dataset;

    private String[] words;

    @Setup(Level.Trial)
    public void setup() {
        words = new String[wordCount];
        Random random = new Random(1234);
        for (int i = 0; i < wordCount; i++) {
            if ("random".equals(dataset)) {
                words[i] = generateRandomWord(wordLength, random);
            } else {
                words[i] = generateRepeatedWord(wordLength, "a");
            }
        }
    }

    @Benchmark
    public GeneralizedSuffixTree buildTree() {
        GeneralizedSuffixTree tree = new GeneralizedSuffixTree();
        for (int i = 0; i < wordCount; i++) {
            tree.put(words[i], i);
        }
        return tree;
    }

    private static String generateRandomWord(int length, Random random) {
        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append((char) ('a' + random.nextInt(26)));
        }
        return word.toString();
    }

    private static String generateRepeatedWord(int length, String pattern) {
        StringBuilder word = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            word.append(pattern);
        }
        return word.toString();
    }
}
