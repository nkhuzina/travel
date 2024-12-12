package com.tgog.service;

import com.tgog.config.AppProporties;
import com.tgog.constants.ApplicationStatus;
import com.tgog.model.Contact;
import com.tgog.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    AppProporties appProporties;

    public Contact saveMsgDetails(Contact contact) {
//        boolean isSaved = false;
        contact.setStatus(ApplicationStatus.OPEN);
        return contactRepository.save(contact);
//        if (null != res && res.getContactId() > 0) {
//            isSaved = true;
//        }
//        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = appProporties.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(ApplicationStatus.OPEN, pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        int rows = contactRepository.updateMsgStatus(ApplicationStatus.CLOSE, contactId);
        if(rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
