mport java.util.*;

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> events = new ArrayList<>();

        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2], b[1]});
            events.add(new int[]{b[1], 0, 0});
        }

        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        PriorityQueue<long[]> heap = new PriorityQueue<>(
            (a, b) -> Long.compare(b[0], a[0])
        );

        heap.offer(new long[]{0, Long.MAX_VALUE});

        List<List<Integer>> result = new ArrayList<>();
        int prevHeight = 0;
        int i = 0;

        while (i < events.size()) {
            int x = events.get(i)[0];

            while (i < events.size() && events.get(i)[0] == x) {
                int[] event = events.get(i);

                if (event[1] < 0) {
                    heap.offer(new long[]{-((long) event[1]), event[2]});
                }

                i++;
            }

            while (!heap.isEmpty() && heap.peek()[1] <= x) {
                heap.poll();
            }

            int currHeight = (int) heap.peek()[0];

            if (currHeight != prevHeight) {
                result.add(Arrays.asList(x, currHeight));
                prevHeight = currHeight;
            }
        }

        return result;
    }
}
