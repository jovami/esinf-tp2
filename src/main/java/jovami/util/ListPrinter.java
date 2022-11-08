package jovami.util;

import java.io.PrintStream;
import java.util.Collection;

/**
 * ListPrinter
 */
public class ListPrinter {

    /**
     * @param <E> Objecto armazenado na colecao
     * @param list Colecao a imprimir
     * @param header Cabecalho da lista, se nao for null
     * @param stream A stream para onde imprimir; System.out se for null
     */
    public static <E> void print(Collection<E> list, String header, PrintStream stream) {
        int i = 0;

        if (list == null || list.isEmpty())
            throw new IllegalArgumentException("erro: lista vazia");

        if (stream == null)
            stream = System.out;

        if (header != null)
            stream.println(header);

        for (E e : list)
            stream.printf("%d. %s\n", ++i, e);
    }
}

