package com.edsandrof.softdesign.resources;

import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.services.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberResource {
    private static final String V1_ENDPOINT = "/api/v1/member";
    private static final String CONTENT_TYPE_JSON = "application/json";

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "List all members")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Returns a list of members"))
    @GetMapping(value = V1_ENDPOINT, produces = CONTENT_TYPE_JSON)
    public ResponseEntity<List<Member>> findAll() {
        List<Member> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }
}
