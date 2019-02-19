public abstract class AbstractProgram1 {
    public abstract boolean isStableMatching(Matching allocation);

    /**
     * Brute force solution to the Stable Marriage problem. Relies on the function
     * isStableMatching(Matching) to determine whether a candidate Matching is stable.
     *
     * @return A stable Matching.
     */
    public Matching stableMarriageBruteForce(Matching allocation) {
        long startTime = System.nanoTime();

        int n = allocation.getUserCount();
        int slots = allocation.totalServerSlots();

        Permutation p = new Permutation(n, slots);
        Matching matching;
        int num = 0;
        while ((matching = p.getNextMatching(allocation)) != null) {
            num++;
            if (isStableMatching(matching)) {

                long endTime   = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println(totalTime);

                return matching;
            }
        }
        return new Matching(allocation);
    }

    public abstract Matching stableMarriageGaleShapley(Matching allocation);
}
