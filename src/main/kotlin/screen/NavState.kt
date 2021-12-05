package screen

enum class NavState {
    Main,
    Category,//+
    CategoryEdit,//+
    Cathedra,//+
    CathedraEdit,//+
    DiplomaEdit,
    PostGraduateDetails,
    PostGraduateEdit,
    PostGraduatesByCategory,//+
    PostGraduatesByCathedra,//+
    PostGraduatesByDirector,//+
    ProtectionByCouncil,
    ProtectionEdit,
    RewardEdit,
    Council,
    CouncilEdit,
    Direction,
    DirectionEdit,
    DirectorByCathedra,//+
    DirectorEdit,//+
    PublicationEdit,
    PublicationByDirector,
    PublicationByPostGraduate;

    var payload: Any? = null
        get() {
            val payload = field
            field = null
            return payload
        }
}