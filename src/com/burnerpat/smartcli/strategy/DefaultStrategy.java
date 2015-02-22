package com.burnerpat.smartcli.strategy;

import com.burnerpat.smartcli.argument.Argument;
import com.burnerpat.smartcli.helper.TableBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Default implementation for the different strategies.
 */
public class DefaultStrategy implements UsageBuilder {

    public static final String NEWLINE = "\n";
    public static final String DOUBLE_NEWLINE = NEWLINE + NEWLINE;

    public static final String TAB = "    ";
    public static final String INDENT = TAB;
    public static final String DOUBLE_INDENT = INDENT + INDENT;
    public static final String COLUMN_SEPARATOR = "   ";

    /**
     * @see com.burnerpat.smartcli.strategy.UsageBuilder#buildUsage(String, String, String, java.util.List, java.util.List, com.burnerpat.smartcli.argument.Argument)
     */
    @Override
    public String buildUsage(String appName, String appDocumentation, String prefix, List<Argument> switches, List<Argument> arguments, Argument defaultArgument) {
        StringBuilder builder = new StringBuilder();

        builder.append("usage: ");
        builder.append(appName);

        if (switches.size() > 0) {
            builder.append(" [options]");
        }

        if (arguments.size() > 0) {
            builder.append(" [arguments]");
        }

        if (defaultArgument != null) {
            for (String name : defaultArgument.getNames()) {
                builder.append(" ");
                builder.append(name);
            }
        }

        if (appDocumentation != null) {
            builder.append(NEWLINE);
            builder.append(appDocumentation);
        }

        if (switches.size() > 0) {
            builder.append(DOUBLE_NEWLINE);

            builder.append(INDENT);
            builder.append("options:");
            builder.append(NEWLINE);

            String[][] cells = new String[switches.size()][2];
            for (int i = 0; i < switches.size(); i++) {
                Argument argument = switches.get(i);
                cells[i][0] = argument.formatNames(prefix);
                cells[i][1] = (argument.getHelpText() != null) ? argument.getHelpText() : "";
            }

            Arrays.sort(cells, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    return o1[0].compareTo(o2[0]);
                }
            });

            builder.append(TableBuilder.buildTable(DOUBLE_INDENT, TAB.length(), COLUMN_SEPARATOR, NEWLINE, cells));
        }

        if (arguments.size() > 0) {
            builder.append(DOUBLE_NEWLINE);

            builder.append(INDENT);
            builder.append("arguments:");
            builder.append(NEWLINE);

            String[][] cells = new String[arguments.size()][3];
            for (int i = 0; i < arguments.size(); i++) {
                Argument argument = arguments.get(i);

                cells[i][0] = argument.formatNames(prefix);
                cells[i][1] = (argument.isRequired()) ? "(required)" : "";
                cells[i][2] = (argument.getHelpText() != null) ? argument.getHelpText() : "";

                if (argument.getFormatHint() != null) {
                    cells[i][2] += "(" + argument.getFormatHint() + ")";
                }
            }

            Arrays.sort(cells, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    return o1[0].compareTo(o2[0]);
                }
            });

            builder.append(TableBuilder.buildTable(DOUBLE_INDENT, TAB.length(), COLUMN_SEPARATOR, NEWLINE, cells));
        }

        return builder.toString();
    }
}
