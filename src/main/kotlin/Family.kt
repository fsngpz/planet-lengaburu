/**
 * The data class represent the entity of Family.
 *
 * @author Ferdinand Sangap.
 * @since 2024-11-06
 */
data class Family(
    val husband: Person?,
    val wife: Person?,
    val parent: Family?
) {
    var children: MutableList<Family>? = null
}
