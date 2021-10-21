const dict = require('../../../karrotRegionHashDict.json');
if (process.argv.length < 2) {
  console.log('too short command');
  process.exit(1);
}

function convertToRegionHash(regionId) {
  return dict[regionId + ''];
}

module.exports = {
  convertToRegionHash,
};
