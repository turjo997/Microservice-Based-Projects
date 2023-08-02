import React from 'react';
import AddEvaluator from './AddEvaluator';

const Evaluator = () => {

  return (
    <div>
      <h1>Add Evaluator</h1>
      <button onClick={handleAddEvaluator}>Add Evaluator</button>
      <p>Welcome to the Evaluator dashboard! Add your content here.</p>
    </div>
  );
};

export default Evaluator;
