const apartmentDataJson = require('../data/apartment.data.json');

function getApartmentByRegionHash(regionHash) {
  const founded = apartmentDataJson.filter(
    (apt) => apt.region_hash_depth4 === regionHash
  );
  if (founded.length === 0) {
    throw new Error(`Cannot found matched Apartment with ${regionHash}`);
  }
  if (founded.length > 1) {
    throw new Error('RegionHashDepth4 cannot matches more than one apartment');
  }
  if (founded.length )
}
