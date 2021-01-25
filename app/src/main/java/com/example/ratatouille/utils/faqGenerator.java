package com.example.ratatouille.utils;

import java.util.ArrayList;

public class faqGenerator {

    public static ArrayList<String> generateGroups() {
        ArrayList<String> faq_group = new ArrayList<>();
        faq_group.add("How to order food?");
        faq_group.add("How to contact the customer support?");
        faq_group.add("What is Ratatouille App?");
        faq_group.add("Is there any way to update my profile?");
        return faq_group;
    }

    public static ArrayList<String> generateChild() {
        ArrayList<String> faq_child = new ArrayList<>();
        String string1 = "Real simple, we only help you to reserve a restaurant not to order food.\n You only need to head to the Home menu, \n there you first need to setup your current location, this will be helped by your Phone's GPS.\n Now you just select your favourite restaurant, and continue to reserve place / review the restaurant.";
        String string2 = "We do not serve a live chat contact support.\n But still, you can reach us by Help Tickets. If your solution doesn't appear in the FAQ List, click the text 'Send us a message instead.' at the bottom of the page.";
        String string3 = "Ratatouille, we simplified the way a restaurant and its customer reach on to another. As simple as that.\n You can get to know your restaurant, how it is performing, and what it serves.";
        String string4 = "Yes there are.\n Just head to the 'Profile' section, then click on the Edit button.\n There you can select your information and change it for free.\n A helpful yet a powerful tool. Still, you need to follow our format terms.";
        faq_child.add(string1);
        faq_child.add(string2);
        faq_child.add(string3);
        faq_child.add(string4);
        return faq_child;
    }
}
