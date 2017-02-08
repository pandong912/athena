package com.ccb.athena.converter.xwork2.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.ccb.athena.converter.config.entities.PackageConfig;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class ConfigurationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationUtil.class);

    private ConfigurationUtil() {
    }

    /**
     * Get the {@link PackageConfig} elements with the specified names.
     * @param configuration Configuration from which to find the package elements
     * @param parent Comma separated list of parent package names
     * @return The package elements that correspond to the names in the {@code parent} parameter.
     */
    public static List<PackageConfig> buildParentsFromString(Configuration configuration, String parent) {
        List<String> parentPackageNames = buildParentListFromString(parent);
        List<PackageConfig> parentPackageConfigs = new ArrayList<PackageConfig>();
        for (String parentPackageName : parentPackageNames) {
            PackageConfig parentPackageContext = configuration.getPackageConfig(parentPackageName);

            if (parentPackageContext != null) {
                parentPackageConfigs.add(parentPackageContext);
            }
        }

        return parentPackageConfigs;
    }

    /**
     * Splits the string into a list using a comma as the token separator.
     * @param parent The comma separated string.
     * @return A list of tokens from the specified string.
     */
    public static List<String> buildParentListFromString(String parent) {
        if ((parent == null) || ("".equals(parent))) {
            return Collections.emptyList();
        }

        StringTokenizer tokenizer = new StringTokenizer(parent, ",");
        List<String> parents = new ArrayList<String>();

        while (tokenizer.hasMoreTokens()) {
            String parentName = tokenizer.nextToken().trim();

            if (!"".equals(parentName)) {
                parents.add(parentName);
            }
        }

        return parents;
    }
}
