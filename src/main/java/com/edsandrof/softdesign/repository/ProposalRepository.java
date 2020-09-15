package com.edsandrof.softdesign.repository;

import com.edsandrof.softdesign.model.Proposal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends MongoRepository<Proposal, String> {
}
