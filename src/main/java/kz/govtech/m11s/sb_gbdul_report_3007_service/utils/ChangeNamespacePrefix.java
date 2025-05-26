package kz.govtech.m11s.sb_gbdul_report_3007_service.utils;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class ChangeNamespacePrefix extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://kdp.3007.reports.egp.gbdul.tamur.kz".equals(namespaceUri)) {
            return "ns2";
        }
        return suggestion;
    }

}
