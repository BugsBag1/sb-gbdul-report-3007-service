package kz.govtech.m11s.sb_gbdul_report_3007_service.utils;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class ChangeNamespacePrefix extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://3005_kdp.reports.egp.gbdul.tamur.kz".equals(namespaceUri)) {
            return "ns2";
        }
        return suggestion;
    }

}
