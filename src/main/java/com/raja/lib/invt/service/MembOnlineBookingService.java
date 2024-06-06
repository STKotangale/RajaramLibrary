package com.raja.lib.invt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.MembOnlineBooking;
import com.raja.lib.invt.objects.GetAllMemberBookings;
import com.raja.lib.invt.objects.OnlineBookingDetails;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.MembOnlineBookingRepository;
import com.raja.lib.invt.request.MembOnlineBookingRequestDTO;
import com.raja.lib.invt.resposne.MembOnlineBookingResponseDTO;

@Service
public class MembOnlineBookingService {

    @Autowired
    private MembOnlineBookingRepository membOnlineBookingRepository;

    @Autowired
    private GeneralMemberRepository generalMemberRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<GetAllMemberBookings> getAllBookings(int userId) {
        return membOnlineBookingRepository.findAllMemberOnlineBooking(userId);
    }

    public MembOnlineBookingResponseDTO getBookingById(int bookingId) {
        MembOnlineBooking booking = membOnlineBookingRepository.findById(bookingId).orElse(null);
        return booking != null ? convertToResponseDTO(booking) : null;
    }

    @Transactional
    public MembOnlineBookingResponseDTO saveBooking(MembOnlineBookingRequestDTO bookingRequest) {
        MembOnlineBooking booking = new MembOnlineBooking();
        updateBookingFromRequest(booking, bookingRequest);
        return convertToResponseDTO(membOnlineBookingRepository.save(booking));
    }

    @Transactional
    public MembOnlineBookingResponseDTO updateBooking(int bookingId, MembOnlineBookingRequestDTO bookingRequest) {
        MembOnlineBooking booking = membOnlineBookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            updateBookingFromRequest(booking, bookingRequest);
            return convertToResponseDTO(membOnlineBookingRepository.save(booking));
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteBooking(int bookingId) {
        membOnlineBookingRepository.deleteById(bookingId);
    }

    private void updateBookingFromRequest(MembOnlineBooking booking, MembOnlineBookingRequestDTO bookingRequest) {
        booking.setInvoiceNo(bookingRequest.getInvoiceNo());
        booking.setInvoiceDate(bookingRequest.getInvoiceDate());
        
        GeneralMember member = generalMemberRepository.findById(bookingRequest.getMemberIdF())
                .orElseThrow(() -> new IllegalArgumentException("Invalid memberId: " + bookingRequest.getMemberIdF()));
        booking.setGeneralMember(member);

        Book book = bookRepository.findById(bookingRequest.getBookIdF())
                .orElseThrow(() -> new IllegalArgumentException("Invalid bookId: " + bookingRequest.getBookIdF()));
        booking.setBook(book);
    }

    private MembOnlineBookingResponseDTO convertToResponseDTO(MembOnlineBooking booking) {
        return new MembOnlineBookingResponseDTO(
            booking.getMembOnlineId(),
            booking.getInvoiceNo(),
            booking.getInvoiceDate(),	
            booking.getGeneralMember().getMemberId(),
            booking.getBook().getBookId()
        );
    }
    
    
    public List<OnlineBookingDetails> getBookingsByUserId(int memberId) {
        return membOnlineBookingRepository.findOnlineBookingsByMemberId(memberId);
    }

}
