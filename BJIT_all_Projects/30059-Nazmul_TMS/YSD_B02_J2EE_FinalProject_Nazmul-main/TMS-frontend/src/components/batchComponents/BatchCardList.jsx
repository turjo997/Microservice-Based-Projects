import React from 'react';
import BatchCard from './BatchCard';

const BatchCardList = ({ batches }) => {
  console.log(batches[0]);
  return (
    <div className="batch-list" style={{display:'flex',flexWrap:'wrap', justifyContent:'flex-start'}}>
      {batches.map((batch) => (
        <BatchCard key={batch.batchId} batch={batch} />
      ))}
    </div>
  );
};

export default BatchCardList;
