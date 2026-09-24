# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 1.2.3 - Fabric Port

- Ported to upstream version 1.2.3 (2026-06-21), requires Create Fabric 6.0.8+

### Fixed

- Sequenced pulse generator getting stuck in "wait until" instructions (#296, from upstream 1.3.3)
- Server crash when items are inserted into an Item Silo with a comparator attached
- Mechanical arms charging or discharging Kinetic Batteries when only checking for a deposit target
- Copycat blocks not craftable in the stonecutter due to a wrong zinc ingot tag
- Copycat conversion recipes turning copycats into themselves instead of the Copycats+ equivalent
- JEI integration not loading
- Feature toggles not being synced from the server to clients
- Fan dyeing catalysts not working with Create: Dragons Plus
- Missing fire texture on the Fan Enriched Catalyst with Create: Nuclear
- Invisible items for the Fan Ending Catalyst with Dragon Head and the Fan Exploding Catalyst

## 1.1.13 - Fabric Port

### Added

- Initial Fabric port based on Forge version 1.1.13 (2026-02-08)
- Requires Create Fabric 6.0.8+

### Fixed (from Forge 1.1.13)

- Redstone link compatibility with Valkyrien Skies 2 (#207, thanks @PopSlime)
- Linked transmitter frequency being broken on servers (#206, thanks @PopSlime)
