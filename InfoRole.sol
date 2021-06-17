pragma solidity ^0.4.0;

contract InfoRole {
    
    struct Record {
        address userName;
        string amount;
    }
    
    mapping (string => string) RecordOf;

    
    function set(string userName, string messageEn) {
        RecordOf[userName] = messageEn;
    }
    
    function get(string userName) constant returns (string retVal) {
        return RecordOf[userName];
    }
}