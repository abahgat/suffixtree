package org.apache.commons.lang3.text.suffixtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StringUtils2;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class LcsTest {

    @Rule
    public BenchmarkRule benchmarkRun = new BenchmarkRule();

    private static List<String> strings = new ArrayList<String>();

    @BeforeClass
    public static void setup() {
        int max = 2;
        for (int i = 0; i < max; i++) {
            strings.add(RandomStringUtils.randomAlphanumeric(10000));
        }
    }

    @Test
    public void testSuffixTree() {
        Collection<? extends CharSequence> result = StringUtils2.lcs(strings.toArray(new String[]{}));
        System.out.println(result);
    }

    @Test
    public void testDynamicProgramming() {
         Collection<CharSequence> c = StringUtils2.getLcs(strings.get(0), strings.get(1));
         System.out.println(c);
    }

    // some other tests
    public static void main(String[] args) throws Exception {
        int max = 2;
        for (int i = 0; i < max; i++) {
            strings.add(RandomStringUtils.randomAlphanumeric(100));
        }

        GeneralizedSuffixTree tree = new GeneralizedSuffixTree();

        for (String s : strings) {
            tree.addSequence(s);
        }

        Collection<String> lcs = tree.lcs();
        System.out.println(lcs);
        
        System.out.println(memoryUsed());
        System.gc();
        System.out.println(memoryUsed());
    }

    static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    static final Runtime runtime = Runtime.getRuntime();
}
