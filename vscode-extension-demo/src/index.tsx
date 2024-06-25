import React, { useState } from 'react';
import ReactDOM from 'react-dom';

const App = () => {
    const [sex, setSex] = useState('');
    const handleChange = (event: any) => {
        setSex(event.target.value);
        vscode.postMessage({ command: "sexChange", content: event.target.value });
    };
    const handleNameChange = (event: any) => {
        const value = event.target.value;
        vscode.postMessage({ command: "nameChange", content: value });
    }
    const handelAgeChange = (event: any) => {
        const value = event.target.value;
        vscode.postMessage({ command: "ageChange", content: value });
    }
    return (
        <div>
            <div>
                <input type="text" name="name" onChange={handleNameChange}></input>
            </div>
            <div>
                <input type="text" name="age" onChange={handelAgeChange}></input>
            </div>
            <div>
                <select value={sex} onChange={handleChange} >
                    <option>girl</option>
                    <option>boy</option>
                </select>
            </div>
            <div>
                <button>ShowIt</button>
            </div>
        </div>
    );
};
const rootElement = document.getElementById('root');
if (rootElement) {
    const root = (ReactDOM as any).createRoot(rootElement);
    root.render(<App />);
} else {
    console.error('Root element not found!');
}

