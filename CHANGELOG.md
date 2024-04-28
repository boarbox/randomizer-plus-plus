# Change Log
 
## [0.0.2+alpha.2-1.20.4] - 2024-04-28

This version contains general improvements to allow better addition of more features in the future.

### Added
 
### Changed

- Randomization algorithm now is the same as the one used by Minecraft
   
### Fixed
 
- Bug where a non-fatal error was thrown due to datapack zip files not properly being closed

## [0.0.2+alpha.1-1.20.4] - 2024-04-14

### Added

- Basic spoiler log file generated in the save root directory (this will be expanded on to be more user friendly in the future)
-  Client side UI when creating a new world to select different per seed options (for now only enable/disable is implemented)
- Began working on how to generate a world for a dedicated server (this is still WIP and not recommended yet)
  - To use on a server, you must create a file named randomizer-plus-plus.settings.json at the root directory of the server
  - In this file you'll need to have the following contents
  
  ```
  {
      "enabled": <true|false>
  }
  ```

### Changed

### Fixed

## [0.0.1+alpha.2-1.20.4] - 2024-04-08

This version is purely for development workflow and has no changes to the mod itself

### Added

### Changed

### Fixed

## [0.0.1+alpha.1-1.20.4] - 2024-04-07

## Added

- World seed based randomization
- Block loot tables are randomized
