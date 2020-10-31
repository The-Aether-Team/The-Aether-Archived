![Banner image](https://gitea.gildedgames.com/GildedGames/The-Aether/raw/branch/1.12.2/doc/banner.webp)
# The Aether Ascension
[![Code license (GNU GPL 3.0)](https://img.shields.io/badge/code%20license-GNU%20GPLv3-green.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0.en.html)
[![Asset license (Unlicensed)](https://img.shields.io/badge/assets%20license-All%20Rights%20Reserved-red.svg?style=flat-square)](https://creativecommons.org/licenses/by-sa/4.0/)

This is a fork of Aether Legacy with the aim is to create an Aether mod that retains the original mod's faithfulness to vanilla Minecraft with more content and quality of life changes. So far, this vision has been carried out in the form of backporting content from Aether II, but various extra tweaks, features, and additions have been added as well.    

Changes made so far:
  - Added Aether II aerclouds (green, purple, storm). Additionally:
    * Textures edited to be monochrome so that they can be tinted, like Aether I aerclouds (including pink)
    * All bouncy aerclouds now make the bounce sound from Aether II
    * All bouncy aerclouds won't bounce if sneak is held, not just blue
    * Storm aerclouds now drip water below them
    * Pink aerclouds now generate by default
  - All mounts are controlled clientside like horses (no more laggy Moas!)
  - Added new grass types from Aether II (arctic, magnetic, irradiated)
  - Added tallgrass from Aether II (generates by default now)
  - Skyroot tools with silk touch will now drop double of the silk touched block, not just double of the normal drop
  - Changed Neptune and Phoenix armor textures to the Aether II: Genesis of the Void versions to make them unique
  - Water is now the Aether II color
  - New tree types from Genesis of the Void
    * Blue - normal, massive
    * Dark Blue - massive + branches
    * Purple - "fruit tree" but without the fruit, as crystal trees have fruit already
  - Added Aether Grass Paths (idea by 💎☁Blade Foxfairy✎🎮#0429)
  - Added Ambrosium Lamps (idea and texture by 💎☁Blade Foxfairy✎🎮#0429 on the Aether Discord)
  - Fixed bugs/improvements:
    * No more errors in console due to Sheepuff loot tables not having name fields
    * Fixed Valkyrie Queens from only checking the first inventory slot containing medals to see if you have enough
    * Made holystone, quicksoil, and Aether ores have more vanilla-standard hardness/resistance values
    * Aether grass, dirt, and quicksoil are now efficient with shovels

Things to do:
  - Bosses more gracefully support multiple players (tried this already but was having trouble)
  - New biomes from Aether II

Original README begins below:

## :heart: Support Gilded Games

[![Patreon pledgers](https://img.shields.io/badge/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.herokuapp.com%2FGildedGames&style=flat-square)](https://patreon.com/GildedGames)
[![Discord user count](https://img.shields.io/discord/118816101936267265.svg?logoColor=FFFFFF&logo=discord&color=7289DA&style=flat-square)](https://discord.gg/YgTv7Vg)
[![Twitter followers](https://img.shields.io/twitter/follow/DevAether.svg?logo=twitter&label=twitter&style=flat-square)](https://twitter.com/DevAether)

If you enjoy our work, [please consider making a pledge](https://patreon.com/GildedGames) today to help fund development and gain access to special perks. Every pledge goes directly into the development process and enables us to continue making the Minecraft mods you know and love.

You can also support the Aether project and Gilded Games by telling your friends, joining our Discord server, and sharing our progress and announcements on social media. Every bit helps!

## :package: Download the latest releases
### "Stable" builds
[![CurseForge downloads](https://cf.way2muchnoise.eu/full_255308_downloads.svg)](https://minecraft.curseforge.com/projects/the-aether-mod)
[![CurseForge packs](https://cf.way2muchnoise.eu/packs/full_255308_in_packs.svg)](https://minecraft.curseforge.com/projects/the-aether-mod)

We use Curseforge to publish **stable builds** of the Aether for Minecraft. You can download the latest stable builds from our [official page found here](https://minecraft.curseforge.com/projects/the-aether-mod) without signing up for an account, and even install the Aether for Minecraft using the [Twitch Launcher](https://www.curseforge.com/twitch-client) with built-in integration for Curseforge mods. We recommend using our stable Curseforge releases for most people.

### Bleeding edge builds
[![Jenkins build status](https://img.shields.io/jenkins/s/https/jenkins.gildedgames.com/job/The-Aether/job/1.12.2.svg?style=flat-square)](https://jenkins.gildedgames.com/blue/organizations/jenkins/The-Aether/activity)

If you're feeling a bit more adventurous (or a developer has suggested you to do so), we provide **bleeding edge builds** which are produced on [our official Jenkins instance](https://jenkins.gildedgames.com/blue/organizations/jenkins/The-Aether/activity). These builds are very frequently created by an automatic service and contain the latest available code without undergoing any form of quality control. We do not generally recommend users use these builds as they may contain serious issues and will not generally receive support.

## :bug: Report bugs or other issues
If you're running into bugs or other problems, feel free to open an issue on our [issue tracker](https://gitea.gildedgames.com/GildedGames/The-Aether/issues). When doing so, we ask that you provide the following information:

- The exact version of the Aether you are running, such as `1.12.2-1.5.1`, and the version of Forge you are using, such as `14.23.5.2847`. Please do not state "the latest stable release" or "latest Forge".
- If your issue is a bug or otherwise unexpected behavior, state what you expected to happen.
- If your issue is a crash, attach the latest client or server log and the complete crash report as a file.
- If your issue only occurs with other mods/plugins installed, list the exact mod/plugin versions installed.

Make sure to keep your issue's description clear and concise. Your issue's title should also be easy to digest, giving our developers and reporters a good idea of what's wrong without including too many details. Failure to follow any of the above may result in your issue being closed.

## :wrench: Contribute to the project
Looking to contribute to the project? We ask that you read over our [Contributor's Guide](https://gitea.gildedgames.com/GildedGames/The-Aether/src/branch/1.12.2/CONTRIBUTING.md) for more details and our Contributor License Agreement (CLA) before getting started.

Not sure what to help with? Take a look at our issue tracker for some ideas! [Here's a quick link](https://gitea.gildedgames.com/GildedGames/The-Aether/issues?label_name%5B%5D=Contributions+Welcome) which shows all the currently open issues that we'd love some help on.

## :scroll: License information
If you're wanting to create a gameplay video/review, extension or addon, parody, or any other fan work of your own for the Aether, go for it! We love seeing the content our community creates, and we hope to make it as welcoming as possible for everyone. We ask however that if you are using code or assets from The Aether project that you adhere to the licenses below, and that you please don't advertise using our brand. If you're interested in sponsoring The Aether project or Gilded Games or wish to otherwise use our brand, please [contact us](mailto:support@gildedgames.com).

The source code of The Aether mod for Minecraft 1.7+ is under the [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html) license. **All assets of the The Aether for Minecraft 1.7+ (found in the [resources folder](https://gitea.gildedgames.com/GildedGames/The-Aether/src/branch/1.12.2/src/main/resources/assets)) are unlicensed and all rights are reserved to them by Gilded Games.** 

[Wiki contributions](https://gitea.gildedgames.com/GildedGames/Aether-II/wiki) are under the [CC BY-SA 4.0 license](https://creativecommons.org/licenses/by-sa/4.0/) unless otherwise stated.

Previous versions of The Aether are not licensed freely and all rights are reserved to them by Gilded Games. Unlicensed assets are owned and copyrighted by their respective authors and all other rights to them are reserved by Gilded Games. If you would like to use
our unlicensed assets, please [get in contact with us](mailto:support@gildedgames.com) for details.

## :star2: Special mentions
### :speech_balloon: Translations
The Aether project makes use of crowd sourced translations. The Aether is available in these languages thanks to the following contributors:

- Chinese (zh_CN, zh_TW) - Translations by: ETStareak, y830705
- Dutch (nl_NL) - Translations by: Critical
- French (fr_FR) - Translations by: lololoustau and Beethov46
- German (de_DE)
- Italian * (it_IT)
- Korean * (ko_KR)
- Polish * (pl_PL)
- Romanian (ro_RO)
- Russian (ru_RU) - Translations by: LeyxorCheysen, andreyalex1236, LunarP1, and zombi1944
- Spanish * (es_MX) - Translations by: Aer-ink
- Thai * (th_TH)
- Ukranian * (uk_UK)

_\* Indicates only partial language support._

The above list is incomplete, and as such, if your name is missing from it, please contact us. **You can help translate The Aether Mod to additional languages by joining our team of avid translators [here](https://aether.oneskyapp.com/collaboration/project?id=158541).**
