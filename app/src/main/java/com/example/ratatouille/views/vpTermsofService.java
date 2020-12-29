package com.example.ratatouille.views;

public class vpTermsofService {
    private static String terms;

    public static void setTerms(String input){
        terms = input;
    }

    public static String getTerms(){
        generateTerms();
        return terms;
    }

    public static void generateTerms(){
        terms = "Terms of Service (\"Terms\")\n" +
                "Last updated: (add date)\n" +
                "Please read these Terms of Service (\"Terms\", \"Terms of Service\") carefully before using the\n" +
                "Ratatouille App mobile\n" +
                "application (the \"Service\") operated by Ratatouille (\"us\", \"we\", or \"our\").\n" +
                "Your access to and use of the Service is conditioned on your acceptance of and compliance with\n" +
                "these Terms. These Terms apply to all visitors, users and others who access or use the Service.\n" +
                "By accessing or using the Service you agree to be bound by these Terms. If you disagree\n" +
                "with any part of the terms then you may not access the Service.\n" +
                "Termination\n" +
                "We may terminate or suspend access to our Service immediately, without prior notice or liability, for\n" +
                "any reason whatsoever, including without limitation if you breach the Terms.\n" +
                "All provisions of the Terms which by their nature should survive termination shall survive\n" +
                "termination, including, without limitation, ownership provisions, warranty disclaimers, indemnity and\n" +
                "limitations of liability.\n" +
                "Subscriptions\n" +
                "Some parts of the Service are billed on a subscription basis (\"Subscription(s)\"). You will be billed in\n" +
                "advance on a recurring ...\n" +
                "The Subscriptions section is for SaaS businesses. For the full disclosure section or for a\n" +
                "“Purchases” section, create your own Terms of Service.\n" +
                "Content\n" +
                "Our Service allows you to post, link, store, share and otherwise make available certain information,\n" +
                "text, graphics, videos, or other material (\"Content\"). You are responsible for the …\n" +
                "The Content section is for businesses that allow users to create, edit, share, make content on\n" +
                "their websites or apps. For the full disclosure section, create your own Terms of Service.\n" +
                "Links To Other Web Sites\n" +
                "Our Service may contain links to third-party web sites or services that are not owned or controlled\n" +
                "by Ratatouille.\n" +
                "Ratatouille (change this) has no control over, and assumes no responsibility for, the content,\n" +
                "privacy policies, or practices of any third party web sites or services. You further acknowledge and\n" +
                "agree that Ratatouille shall not be responsible or liable, directly or indirectly, for any\n" +
                "damage or loss caused or alleged to be caused by or in connection with use of or reliance on any\n" +
                "such content, goods or services available on or through any such web sites or services.\n" +
                "Changes\n" +
                "We reserve the right, at our sole discretion, to modify or replace these Terms at any time. If a\n" +
                "revision is material we will try to provide at least 30 days' notice prior to any new terms\n" +
                "taking effect. What constitutes a material change will be determined at our sole discretion.\n" +
                "Contact Us\n" +
                "If you have any questions about these Terms, please contact us.";
    }
}
