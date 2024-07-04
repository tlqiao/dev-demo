import React from 'react';

const ElementOverlay = ({ element }) => {
    const { x, y, width, height, attributes } = element;
    const style = {
        position: 'absolute',
        left: x,
        top: y,
        width: width,
        height: height,
        border: '2px solid red',
        pointerEvents: 'none'
    };

    const generateLocator = () => {
        return "myLocator"
        // Generate a simple locator based on element attributes
        // ...
    };

    return (
        <div style={style}>
            <div className="element-info">
                <p>Attributes:</p>
                <pre>{JSON.stringify(attributes, null, 2)}</pre>
                <p>Locator: {generateLocator()}</p>
            </div>
        </div>
    );
};

export default ElementOverlay;
