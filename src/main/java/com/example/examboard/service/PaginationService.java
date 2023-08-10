package com.example.examboard.service;

import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;

@Getter
public class PaginationService {

    private static final int BAR_LENGTH = 5;
    private int startNumber;
    private int endNumber;
    private int currentPage;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        //int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 1);
        //int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages+1);
        currentPage=currentPageNumber;
        endNumber = totalPages;
        int mod = totalPages % BAR_LENGTH;
        if(totalPages - mod >= currentPageNumber) {
            endNumber = (int) (Math.ceil((float)currentPageNumber / BAR_LENGTH) * BAR_LENGTH);
            startNumber = endNumber - 4;
        } else {
            startNumber = (int) (Math.ceil((float)currentPageNumber / BAR_LENGTH) * BAR_LENGTH) - 4;
        }

        return IntStream.range(startNumber, endNumber+1).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
