import java.net.URI

data class DocumentState(
    val documents: Array<Document>,
    val showVehicles: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DocumentState

        if (!documents.contentEquals(other.documents)) return false
        if (showVehicles != other.showVehicles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = documents.contentHashCode()
        result = 31 * result + showVehicles.hashCode()
        return result
    }

    override fun toString(): String {
        return "DocumentState(documents=${documents.contentToString()}, showVehicles=$showVehicles)"
    }


}

data class Document(
    val name: String
){

    override fun toString(): String {
        return "Document(name='$name')"
    }
}