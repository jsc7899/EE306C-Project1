/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching allocation) {
        int m = allocation.getServerCount();
        int n = allocation.getUserCount();
        ArrayList<Integer> user_matching = allocation.getUserMatching();
        ArrayList<ArrayList<Integer>> user_preference = allocation.getUserPreference();
        ArrayList<ArrayList<Integer>> server_preference = allocation.getServerPreference();

        int slots_filled = 0;
        for(int u=0; u < n; u ++) {
            if(user_matching.get(u) != -1) {
                slots_filled++;
            }
        }
        if(slots_filled != allocation.totalServerSlots()) return false;

        for(int u=0; u < n; u++) { //for each user
            if(user_matching.get(u) == -1) continue; // make sure u is assigned
            int s = user_matching.get(u); // s
            // instability 1
            for(int up=0; up < n; up++) {
                if(up == u) continue; // make sure u' is different from u
                int sp = user_matching.get(up); // s'
                if(sp == -1) {
                    ArrayList<Integer> s_pref = server_preference.get(s);
                    for(int i=0; i < s_pref.size(); i++) {
                        if(s_pref.indexOf(u) > s_pref.indexOf(up)) { //if s prefers up to u then instability
                            return false;
                        }
                    }
                }
            }
            // instability 2
            for(int up=0; up < n; up++) {
                int sp = user_matching.get(up); // s'
                if(up == u || sp == -1) continue; // make sure u' is different from u and u' is assigned
                ArrayList<Integer> s_pref = server_preference.get(s);
                for(int i=0; i < s_pref.size(); i++) {
                    if(s_pref.indexOf(u) > s_pref.indexOf(up)) { //if s prefers u' to u
                        ArrayList<Integer> up_pref = user_preference.get(up);
                        for(int j=0; j < up_pref.size(); j++) {
                            if(up_pref.indexOf(s) < up_pref.indexOf(sp)) { // and u' prefers s to s'
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching allocation) {
        //long startTime = System.nanoTime();

        int[] serverSlots = new int[allocation.getServerCount()];
        int m = allocation.getServerCount();
        int n = allocation.getUserCount();
        ArrayList<Integer> user_matching = new ArrayList<>();
        ArrayList<ArrayList<Integer>> user_preference = allocation.getUserPreference();
        ArrayList<ArrayList<Integer>> server_preference = allocation.getServerPreference();

        for(int i = 0; i < allocation.getServerCount(); i++) {
            serverSlots[i] = allocation.getServerSlots().get(i);
        }

        for(int i = 0; i < n; i++) {
            user_matching.add(i, -1);
        }
        while(allocation.totalServerSlots() > 0) {
        for (int s = 0; s < m; s++) {                                                                       // for each server
                for (int k = 0; k < n && allocation.getServerSlots().get(s) > 0; k++) {                     // offer a slot to each user while there are slots available
                    int u = allocation.getServerPreference().get(s).get(k);
                    if (user_matching.get(u) < 0) {
                        user_matching.set(u, s);
                        allocation.getServerSlots().set(s, allocation.getServerSlots().get(s) - 1);         // number of slots at s decreases (one just filled)
                    } else {
                        int sp = user_matching.get(u); // s'
                        if (user_preference.get(u).indexOf(s) < user_preference.get(u).indexOf(sp)) {       // if u does not prefer sp to s
                            user_matching.set(u, s);                                    // match u and s
                            allocation.getServerSlots().set(sp, allocation.getServerSlots().get(sp) + 1);   // number of slots at sp increases (one just removed)
                            allocation.getServerSlots().set(s, allocation.getServerSlots().get(s) - 1);     // number of slots at s decreases (one just filled)
                        }
                    }
                }
            }
        }
        allocation.setUserMatching(user_matching);
        for(int i = 0; i < allocation.getServerCount(); i++) {
            allocation.getServerSlots().set(i, serverSlots[i]);
        }
        /*
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        */
        return allocation;
    }
}
