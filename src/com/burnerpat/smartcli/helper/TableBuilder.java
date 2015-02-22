package com.burnerpat.smartcli.helper;

/**
 * Helper class to create table formatted strings.
 */
public class TableBuilder {

    /**
     * Creates a table formatted string using the specified arguments.
     *
     * @param prefix A string preceding every line of the table.
     * @param tabLength The amount of space characters that form a tab block in the table.
     * @param columnSeparator The separator to put between two columns.
     * @param newline The newline character to put at the end of every line.
     * @param cells The cell data. It has to be a MxN sized matrix, where every line is of the same length as every
     *              other line and every column is of the same length as every other column.
     *
     * @return The table formatted string.
     */
    public static String buildTable(String prefix, int tabLength, String columnSeparator, String newline, String[][] cells) {
        StringBuilder builder = new StringBuilder();
        int[] sizes = new int[cells[0].length];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 0;
        }

        for (String[] line : cells) {
            for (int i = 0; i < line.length; i++) {
                int l = line[i].length();
                if (l > sizes[i]) {
                    sizes[i] = l;
                }
            }
        }

        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = (int) Math.ceil((double) sizes[i] / (double) tabLength) * tabLength + columnSeparator.length();
        }

        for (int l = 0; l < cells.length; l++) {
            String[] line = cells[l];
            builder.append(prefix);

            for (int i = 0; i < line.length; i++) {
                String cell = line[i];
                builder.append(cell);

                for (int j = 0; j < (sizes[i] - cell.length()); j++) {
                    builder.append(" ");
                }
            }

            if (l < cells.length - 1) {
                builder.append(newline);
            }
        }

        return builder.toString();
    }
}
