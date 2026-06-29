package com.example.cardcalculation.view;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardcalculation.data.model.Bid;
import com.example.cardcalculation.repository.BidRepository;

import java.util.List;

public class BidViewModel extends AndroidViewModel {
    private BidRepository bidRepository;

    public BidViewModel(Application application) {
        super(application);
        bidRepository = new BidRepository(application);
    }

    public LiveData<List<Bid>> getAllBids(int gameId) {
        return bidRepository.getAllBids(gameId);
    }

    public LiveData<Bid> getBidById(int bidId) {
        return bidRepository.getBidById(bidId);
    }

    public void insert(Bid bid) {
        bidRepository.insert(bid);
    }

    public void update(Bid bid) {
        bidRepository.update(bid);
    }

    public void delete(Bid bid) {
        bidRepository.delete(bid);
    }
}
