////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

public enum EItemType {
    PROCESSOR("PROCESSOR"),
    MOTHERBOARD("MOTHERBOARD"),
    KEYBOARD("KEYBOARD"),
    MOUSE("MOUSE");

    private final String value;

    EItemType(final String val) {
        this.value = val;
    }
}