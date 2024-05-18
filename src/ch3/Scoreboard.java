package src.ch3;

import java.util.stream.Stream;

// Only stores limited number of scores
// New scores must be higher than lowest high score to be added
//   Lowest score is then removed from list
// scores are sorted from high to low
public class Scoreboard {
    private int numEntries = 0;
    private GameEntry[] board;

    public Scoreboard(int capacity){
        this.board = new GameEntry[capacity];
    }

    public void add(GameEntry e){
        int newScore = e.getScore();

        // check if eligible to be added
        if(this.numEntries < this.board.length || newScore > board[this.numEntries-1].getScore()) {
            if(this.numEntries < this.board.length) this.numEntries++;
            int index = this.numEntries-1;
            while(index > 0 && newScore > this.board[index-1].getScore()) {
                this.board[index] = this.board[index-1];
                index--;
            }
            this.board[index] = e;
        }
    }

    public GameEntry remove(int index){
        if(index >= this.numEntries) throw new IndexOutOfBoundsException();
        var removed = this.board[index];
        this.board[index]=null;
        int next = index + 1;
        while(next < numEntries) {
            this.board[next-1] = this.board[next];
            next++;
        }
        return removed;
    }

    public static void main(String[] args) {
        int letter = 65;
        int score = 5;
        var entries = Stream.generate(() => {
            String name = Character.
            var entry = new GameEntry(Integer.t, score)
        })
    }
}
