package net.milanqiu.mimas.guava;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * An extension of {@link com.google.common.io.Files} to provide more utilities.
 * <p>
 * Creation Date: 2021-06-13
 * @author Milan Qiu
 */
public class FilesExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private FilesExt() {}

    /**
     * Overwrites a file with the contents of an iterable of character sequences, using the given character set.
     * @param lines the iterable of character sequences to write
     * @param toFile the destination file
     * @param charset the character set used to encode the lines
     * @throws IOException if an I/O error occurs
     */
    public static void writeLines(Iterable<? extends CharSequence> lines, File toFile, Charset charset) throws IOException {
        Files.asCharSink(toFile, charset).writeLines(lines);
    }

    /**
     * Overwrites a file with the contents of an iterable of character sequences and specified line separator, using the given character set.
     * @param lines the iterable of character sequences to write
     * @param lineSeparator the line separator to write
     * @param toFile the destination file
     * @param charset the character set used to encode the lines
     * @throws IOException if an I/O error occurs
     */
    public static void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator, File toFile, Charset charset) throws IOException {
        Files.asCharSink(toFile, charset).writeLines(lines, lineSeparator);
    }
}
