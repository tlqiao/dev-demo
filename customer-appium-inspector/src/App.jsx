import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';

const App = () => {
    const [pageSource, setPageSource] = useState('');
    const [screenshot, setScreenshot] = useState('');
    const [elementInfo, setElementInfo] = useState(null);
    const [highlightBounds, setHighlightBounds] = useState(null);
    const imageRef = useRef(null);
    const ERROR_MARGIN = 5; // 可以调整误差范围

    const getPageSource = async () => {
        try {
            const response = await axios.get('http://localhost:9096/page-source');
            setPageSource(response.data);
        } catch (err) {
            console.error('Error fetching page source:', err);
        }
    };
    const getScreenshot = async () => {
        try {
            const response = await axios.get('http://localhost:9096/screenshot');
            setScreenshot(`data:image/png;base64,${response.data}`);
        } catch (err) {
            console.error('Error fetching screenshot:', err);
        }
    };
    useEffect( () => {
         getPageSource();
         getScreenshot()
    }, []);

    const handleImageClick = (event) => {
        if (imageRef.current && pageSource) {
            const rect = imageRef.current.getBoundingClientRect();
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;
            // 检索页面源数据中的元素
            pageSource.hierarchy.$.bounds="[0,0][1080,2208]";
            const element = findElementAtCoordinates(pageSource.hierarchy, x, y);
            if (element) {
                setElementInfo(element.$);
                const bounds = parseBounds(element.$.bounds);
                setHighlightBounds(bounds);
            } else {
                setElementInfo(null);
                setHighlightBounds(null);
            }
        }
    };
    const parseBounds = (boundsStr) => {
        const bounds = boundsStr.match(/\d+/g).map(Number);
        return {
            left: bounds[0],
            top: bounds[1],
            right: bounds[2],
            bottom: bounds[3],
            centerX: (bounds[0] + bounds[2]) / 2,
            centerY: (bounds[1] + bounds[3]) / 2,
        };
    };

    const findElementAtCoordinates = (node, x, y) => {
        if (!node || !node.$ || !node.$.bounds) {
            return null;
        }
        const bounds = parseBounds(node.$.bounds);

        const withinBounds = (x, y, bounds) => {
            return (
                x >= bounds.left &&
                x <= bounds.right &&
                y >= bounds.top &&
                y <= bounds.bottom
            );
        };

        if (withinBounds(x, y, bounds)) {
            for (const child of Object.values(node)) {
                if (Array.isArray(child)) {
                    for (const grandChild of child) {
                        const foundElement = findElementAtCoordinates(grandChild, x, y);
                        if (foundElement) {
                            return foundElement;
                        }
                    }
                }
            }
            return node;
        }

        return null;
    };

    return (
        <div>
            {screenshot && (
                <div style={{ position: 'relative' }}>
                    <img
                        ref={imageRef}
                        src={screenshot}
                        alt="Mobile App Screenshot"
                        onClick={handleImageClick}
                        style={{ cursor: 'pointer', width: '1080px', height: '2208px' }} // 根据 page source 调整大小
                    />
                    {highlightBounds && (
                        <div
                            style={{
                                position: 'absolute',
                                left: highlightBounds.left,
                                top: highlightBounds.top,
                                width: highlightBounds.right - highlightBounds.left,
                                height: highlightBounds.bottom - highlightBounds.top,
                                border: '2px solid red',
                                pointerEvents: 'none',
                            }}
                        />
                    )}
                </div>
            )}
            {/*{clickedElement && (*/}
            {/*    <div*/}
            {/*        style={{*/}
            {/*            position: 'absolute',*/}
            {/*            left: clickedElement.x,*/}
            {/*            top: clickedElement.y,*/}
            {/*            width: '5px',*/}
            {/*            height: '5px',*/}
            {/*            backgroundColor: 'red',*/}
            {/*        }}*/}
            {/*    />*/}
            {/*)}*/}
            {elementInfo && (
                <div>
                    <h3>Element Info</h3>
                    <pre>{JSON.stringify(elementInfo, null, 2)}</pre>
                </div>
            )}
        </div>
    );
};

export default App;
