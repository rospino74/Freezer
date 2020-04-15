rootProject.name = "Freezer"
include("src:main")
findProject(":src:main")?.name = "main"
