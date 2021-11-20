package screen

enum class NavState {
    Main,
    Category,
    CategoryEdit,
    Cathedra,
    CathedraEdit,
    Diploma,
    DiplomaEdit,
    PostGraduate,
    PostGraduateEdit,
    Protection,
    ProtectionEdit,
    Reward,
    RewardEdit,
    Council,
    CouncilEdit,
    Direction,
    DirectionEdit,
    Director,
    DirectorEdit,
    Publication,
    PublicationEdit,

    /** Учет сведений об аспирантах ВУЗа различных категорий (очные, заочные, соискатели, докторанты) */
    PostGraduatesByCategory,

    /** Формирование списка аспирантов по кафедрам/научным руководителям */
    PostGraduateInfo,

    /** Учет научных публикаций аспирантов/научного руководителя аспиранта */
    PublicationInfo,

    /** Учет работы научных советов */
    CouncilInfo,

    /** Составление отчетов о состоявшихся защитах (по различным категориям, научным направлениям, кафедрам, научным руководителям и т.п.) */
    ProtectionInfo
}